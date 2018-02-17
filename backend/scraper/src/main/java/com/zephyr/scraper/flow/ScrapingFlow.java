package com.zephyr.scraper.flow;

import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.internal.dto.SearchResultDto;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface ScrapingFlow {

    Flux<SearchResultDto> handle(Flux<QueryDto> input);
}
