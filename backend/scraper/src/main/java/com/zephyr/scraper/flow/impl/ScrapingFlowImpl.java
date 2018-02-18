package com.zephyr.scraper.flow.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.crawler.Crawler;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.exceptions.FraudException;
import com.zephyr.scraper.factories.SearchResultFactory;
import com.zephyr.scraper.flow.ScrapingFlow;
import com.zephyr.scraper.request.RequestConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import java.util.function.Function;

@Slf4j
@Component
public class ScrapingFlowImpl implements ScrapingFlow {
    private static final String NEW_QUERY_MSG = "Received new Query: {}";
    private static final String RECEIVED_SEARCH_RESULT_MSG = "Received SearchResult: {}";
    private static final String FRAUD_EXCEPTION_MSG = "Fraud detected. Reporting...";
    private static final String UNEXPECTED_EXCEPTION_MSG = "Unexpected exception: ";

    @Setter(onMethod = @__(@Autowired))
    private RequestConstructor requestConstructor;

    @Setter(onMethod = @__(@Autowired))
    private Browser browser;

    @Setter(onMethod = @__(@Autowired))
    private Crawler crawler;

    @Setter(onMethod = @__(@Autowired))
    private SearchResultFactory searchResultFactory;

    @Override
    public Flux<SearchResultDto> handle(Flux<QueryDto> input) {
        return input.doOnNext(LoggingUtils.info(log, NEW_QUERY_MSG))
                .flatMap(requestConstructor::construct)
                .parallel()
                .flatMap(this::makeRequest)
                .sequential()
                .doOnNext(LoggingUtils.debug(log, RECEIVED_SEARCH_RESULT_MSG))
                .doOnError(LoggingUtils.error(log, UNEXPECTED_EXCEPTION_MSG));
    }

    private Mono<SearchResultDto> makeRequest(EngineRequest request) {
        return Mono.defer(() -> browser.get(request))
                .map(crawler::crawl)
                .retryWhen(fraudException())
                .map(l -> searchResultFactory.create(request, l));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> fraudException() {
        return Retry.anyOf(FraudException.class)
                .doOnRetry(c -> handleFraudException(c.exception()));
    }

    private void handleFraudException(Throwable exception) {
        if (exception instanceof FraudException) {
            log.error(FRAUD_EXCEPTION_MSG);
            browser.report(((FraudException) exception).getResponse());
        }
    }
}