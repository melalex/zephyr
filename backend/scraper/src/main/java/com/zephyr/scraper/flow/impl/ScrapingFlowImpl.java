package com.zephyr.scraper.flow.impl;

import com.zephyr.data.dto.QueryDto;
import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.crawler.Crawler;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.exceptions.FraudException;
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

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class ScrapingFlowImpl implements ScrapingFlow {

    @Setter(onMethod = @__(@Autowired))
    private RequestConstructor requestConstructor;

    @Setter(onMethod = @__(@Autowired))
    private Browser browser;

    @Setter(onMethod = @__(@Autowired))
    private Crawler crawler;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Override
    public Flux<SearchResultDto> handle(Flux<QueryDto> input) {
        return input.doOnNext(q -> log.info("Received new Query: {}", q))
                .flatMap(requestConstructor::construct)
                .parallel()
                .flatMap(this::makeRequest)
                .sequential();
    }

    private Mono<SearchResultDto> makeRequest(EngineRequest request) {
        return Mono.defer(() -> browser.get(request))
                .map(crawler::crawl)
                .retryWhen(fraudException())
                .map(l -> toSearchResult(request, l));
    }

    private SearchResultDto toSearchResult(EngineRequest request, List<String> links) {
        SearchResultDto searchResult = new SearchResultDto();
        searchResult.setOffset(request.getOffset());
        searchResult.setQuery(request.getQuery());
        searchResult.setProvider(request.getProvider());
        searchResult.setTimestamp(LocalDateTime.now(clock));
        searchResult.setLinks(links);

        return searchResult;
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> fraudException() {
        return Retry.anyOf(FraudException.class)
                .doOnRetry(c -> handleFraudException(c.exception()));
    }

    private void handleFraudException(Throwable exception) {
        if (exception instanceof FraudException) {
            browser.report(((FraudException) exception).getResponse());
        }
    }
}