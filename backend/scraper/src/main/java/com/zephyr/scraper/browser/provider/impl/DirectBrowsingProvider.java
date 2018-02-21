package com.zephyr.scraper.browser.provider.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.properties.ScraperProperties;
import com.zephyr.scraper.scheduling.SchedulingManager;
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
public class DirectBrowsingProvider extends AbstractBrowsingProvider {
    private static final String NEW_REQUEST_MSG = "Scheduled new request with id {}";
    private static final String NEW_RESPONSE_MSG = "Received response for request with id %s";
    private static final String NEW_RESPONSE_FULL_MSG = "Received response for\n{}";
    private static final String BROWSER_EXCEPTION_MSG = "Exception during request: ";
    private static final String REQUEST_EXCEPTION_MSG = "Exception during request on %s try: ";

    @Setter(onMethod = @__(@Autowired))
    private SchedulingManager schedulingManager;

    @Setter(onMethod = @__(@Autowired))
    private AsyncHttpClient asyncHttpClient;

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    public DirectBrowsingProvider() {
        super(ScraperProperties.RequestType.DIRECT);
    }

    @Override
    public Mono<EngineResponse> get(EngineRequest request) {
        SearchEngine engine = request.getProvider();

        log.info(NEW_REQUEST_MSG, request.getId());

        return Mono.empty()
                .delaySubscription(schedulingManager.scheduleNext(engine, delay(engine)))
                .then(makeRequest(request))
                .doOnError(LoggingUtils.error(log, BROWSER_EXCEPTION_MSG))
                .retryWhen(browserException(request));
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
                .doOnNext(LoggingUtils.info(log, EngineResponse::getId, NEW_RESPONSE_MSG))
                .doOnNext(LoggingUtils.debug(log, NEW_RESPONSE_FULL_MSG))
                .retryWhen(requestException());
    }

    private void report(SearchEngine engine) {
        schedulingManager.reSchedule(engine, errorDelay(engine).minus(delay(engine)));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> requestException() {
        return Retry.any()
                .retryMax(properties.getBrowser().getRetryCount())
                .fixedBackoff(Duration.ofMillis(properties.getBrowser().getBackOff()))
                .doOnRetry(LoggingUtils.retryableError(log, REQUEST_EXCEPTION_MSG));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> browserException(EngineRequest engineRequest) {
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

    private EngineResponse toEngineResponse(Response response, EngineRequest engineRequest) {
        return EngineResponse.builder()
                .id(engineRequest.getId())
                .headers(getHeaders(response.getHeaders()))
                .body(response.getResponseBody())
                .provider(engineRequest.getProvider())
                .build();
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
