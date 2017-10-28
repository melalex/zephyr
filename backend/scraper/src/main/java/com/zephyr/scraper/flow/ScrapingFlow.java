package com.zephyr.scraper.flow;

import com.zephyr.data.Keyword;
import com.zephyr.data.SearchResult;
import reactor.core.publisher.Flux;

public interface ScrapingFlow {

    Flux<SearchResult> handle(Flux<Keyword> input);
}
