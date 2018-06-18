package com.zephyr.scraper.browser.provider.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.browser.provider.BrowsingProvider;
import com.zephyr.scraper.browser.support.WebClientWrapper;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.scheduling.SchedulingManager;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;
import reactor.retry.RetryExhaustedException;

import java.util.function.Function;

@Slf4j
@Component
public class DirectBrowsingProvider implements BrowsingProvider {

    private static final String NEW_REQUEST_MSG = "Scheduled new request with id {}";
    private static final String NEW_RESPONSE_MSG = "Received response for request with id {}";
    private static final String BROWSER_EXCEPTION_MSG = "Exception during request: %s";
    private static final String REQUEST_EXCEPTION_MSG = "Exception during request on %s try: ";

    @Setter(onMethod = @__(@Autowired))
    private SchedulingManager schedulingManager;

    @Setter(onMethod = @__(@Autowired))
    private WebClientWrapper webClient;

    @Setter(onMethod = @__(@Autowired))
    private ScraperConfigurationService configuration;

    @Override
    public Mono<EngineResponse> get(EngineRequest request) {
        var engine = request.getProvider();

        log.info(NEW_REQUEST_MSG, request.getId());

        return Mono.empty()
                .delaySubscription(schedulingManager.scheduleNext(engine, configuration.getDelay(engine)))
                .then(makeRequest(request))
                .doOnError(LoggingUtils.error(log, String.format(BROWSER_EXCEPTION_MSG, request)))
                .retryWhen(browserException(request))
                .onErrorMap(RetryExhaustedException.class, Throwable::getCause);
    }

    @Override
    public void onFail(EngineResponse response) {
        report(response.getProvider());
    }

    private Mono<EngineResponse> makeRequest(EngineRequest request) {
        return webClient.get(request.getFullPath(), request.getParams(), request.getHeaders())
                .flatMap(extractEngineResponse(request))
                .doOnNext(LoggingUtils.info(log, EngineResponse::getId, NEW_RESPONSE_MSG))
                .retryWhen(requestException())
                .onErrorMap(RetryExhaustedException.class, Throwable::getCause);
    }

    private void report(SearchEngine engine) {
        schedulingManager.reSchedule(engine, configuration.getErrorDelay(engine).minus(configuration.getDelay(engine)));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> requestException() {
        return Retry.any()
                .retryMax(configuration.getRetryCount())
                .fixedBackoff(configuration.getBackOff())
                .doOnRetry(LoggingUtils.retryableError(log, REQUEST_EXCEPTION_MSG));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> browserException(EngineRequest engineRequest) {
        return Retry.<EngineRequest>any()
                .withApplicationContext(engineRequest)
                .retryMax(Integer.MAX_VALUE)
                .doOnRetry(c -> report(c.applicationContext().getProvider()));
    }

    private Function<ClientResponse, Mono<EngineResponse>> extractEngineResponse(EngineRequest engineRequest) {
        return c -> c.bodyToMono(String.class)
                .map(b -> EngineResponse.builder()
                        .id(engineRequest.getId())
                        .status(c.statusCode().value())
                        .headers(c.headers().asHttpHeaders())
                        .body(b)
                        .provider(engineRequest.getProvider())
                        .build());
    }
}
