package com.zephyr.scraper.browser.provider.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scheduling.managers.SchedulingManager;
import com.zephyr.scraper.browser.provider.BrowsingProvider;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class DirectBrowsingProvider implements BrowsingProvider {

    @Setter(onMethod = @__(@Autowired))
    private SchedulingManager schedulingManager;

    @Setter(onMethod = @__(@Autowired))
    private AsyncHttpClient asyncHttpClient;

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @Override
    public Mono<EngineResponse> get(EngineRequest request) {
        SearchEngine engine = request.getProvider();
        return Mono.empty()
                .delaySubscription(schedulingManager.scheduleNext(engine, delay(engine)))
                .then(makeRequest(request))
                .doOnError(e -> log.error("Exception during request", e))
                .retryWhen(requestException(request));
    }

    @Override
    public void report(EngineResponse response) {
        report(response.getProvider());
    }

    private Mono<EngineResponse> makeRequest(EngineRequest engineRequest) {
        Request request = new RequestBuilder()
                .setFollowRedirect(true)
                .setUrl(engineRequest.getUrl())
                .setQueryParams(engineRequest.getParams())
                .setHeaders(engineRequest.getHeaders())
                .build();

        return Mono.defer(() -> Mono.fromFuture(asyncHttpClient.executeRequest(request).toCompletableFuture()))
                .map(r -> toEngineResponse(r, engineRequest))
                .retryWhen(requestException());
    }

    private void report(SearchEngine engine) {
        schedulingManager.onError(engine, errorDelay(engine).minus(delay(engine)));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> requestException() {
        return Retry.any()
                .retryMax(properties.getBrowser().getRetryCount())
                .fixedBackoff(Duration.ofMillis(properties.getBrowser().getBackoff()))
                .doOnRetry(c ->
                        log.error(String.format("Exception during request on %s try: ", c.iteration()), c.exception()));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> requestException(EngineRequest engineRequest) {
        return Retry.<EngineRequest>any()
                .withApplicationContext(engineRequest)
                .doOnRetry(c -> report(c.applicationContext().getProvider()));
    }

    private Duration delay(SearchEngine engine) {
        return Duration.ofMillis(properties.getScraper(engine).getDelay());
    }

    private Duration errorDelay(SearchEngine engine) {
        return Duration.ofMillis(properties.getScraper(engine).getErrorDelay());
    }

    private EngineResponse toEngineResponse(Response response, EngineRequest request) {
        return EngineResponse.of(getHeaders(response.getHeaders()), response.getResponseBody(), request.getProvider());
    }

    private Map<String, List<String>> getHeaders(HttpHeaders headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> headers.getAll(e.getKey()),
                        (u, v) -> Stream.concat(u.stream(), v.stream()).collect(Collectors.toList())
                ));
    }
}
