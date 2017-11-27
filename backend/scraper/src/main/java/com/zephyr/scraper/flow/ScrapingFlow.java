package com.zephyr.scraper.flow;

import com.zephyr.data.commons.Keyword;
import reactor.core.publisher.Flux;

import javax.naming.directory.SearchResult;

@FunctionalInterface
public interface ScrapingFlow {

    Flux<SearchResult> handle(Flux<Keyword> input);
}
