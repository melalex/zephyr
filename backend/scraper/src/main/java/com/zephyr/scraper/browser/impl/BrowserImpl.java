package com.zephyr.scraper.browser.impl;

import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.browser.provider.BrowsingProvider;
import com.zephyr.scraper.crawler.Crawler;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.exceptions.FraudException;
import com.zephyr.scraper.factories.SearchResultFactory;
import com.zephyr.scraper.locator.SearchEngineManager;
import com.zephyr.scraper.locator.impl.DefaultSearchEngineManager;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class BrowserImpl implements Browser {
    private static final String FRAUD_EXCEPTION_MSG = "Fraud detected for response with id '{}'. Reporting...";

    private SearchEngineManager<BrowsingProvider> browsingManager;

    @Setter(onMethod = @__(@Autowired))
    private Crawler crawler;

    @Setter(onMethod = @__(@Autowired))
    private SearchResultFactory searchResultFactory;

    @Autowired
    public void setBrowsingManager(List<BrowsingProvider> providers) {
        this.browsingManager = DefaultSearchEngineManager.of(providers);
    }

    @Override
    public Mono<SearchResultDto> get(EngineRequest request) {
        return Mono.defer(() -> makeRequest(request))
                .map(crawler::crawl)
                .retryWhen(fraudException())
                .map(l -> searchResultFactory.create(request, l));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> fraudException() {
        return Retry.anyOf(FraudException.class)
                .doOnRetry(c -> report(getResponse(c.exception())));
    }

    private EngineResponse getResponse(Throwable throwable) {
        return ((FraudException) throwable).getResponse();
    }

    private Mono<EngineResponse> makeRequest(EngineRequest engineRequest) {
        return browsingManager.manage(engineRequest.getProvider()).get(engineRequest);
    }

    private void report(EngineResponse response) {
        log.warn(FRAUD_EXCEPTION_MSG, response.getId());
        browsingManager.manage(response.getProvider()).report(response);
    }
}
