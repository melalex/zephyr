package com.zephyr.scraper.browser.provider.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.browser.provider.BrowsingProvider;
import com.zephyr.scraper.browser.provider.internal.Scheduler;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
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
    private Scheduler scheduler;

    @Setter(onMethod = @__(@Autowired))
    private AsyncHttpClient asyncHttpClient;

    @Override
    public Mono<EngineResponse> get(EngineRequest engineRequest) {
        return Mono.defer(() -> Mono.delay(scheduler.reserve(engineRequest.getProvider())))
                .then(makeRequest(engineRequest))
                .doOnError(e -> log.error("Exception during request", e))
                .retryWhen(requestException(engineRequest));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> requestException(EngineRequest engineRequest) {
        return Retry.<EngineRequest>any()
                .withApplicationContext(engineRequest)
                .doOnRetry(c -> report(c.applicationContext().getProvider()));
    }

    @Override
    public void report(EngineResponse response) {
        report(response.getProvider());
    }

    private void report(SearchEngine engine) {
        scheduler.report(engine);
    }

    private Mono<EngineResponse> makeRequest(EngineRequest engineRequest) {
        Request request = new RequestBuilder()
                .setFollowRedirect(true)
                .setUrl(engineRequest.getUrl())
                .setQueryParams(engineRequest.getParams())
                .setHeaders(engineRequest.getHeaders())
                .build();

        return Mono.fromFuture(asyncHttpClient.executeRequest(request).toCompletableFuture())
                .map(r -> toEngineResponse(r, engineRequest));
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
