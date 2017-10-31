package com.zephyr.scraper.flow;

import com.zephyr.data.commons.Keyword;
import com.zephyr.data.dto.SearchResultDto;
import reactor.core.publisher.Flux;

public interface ScrapingFlow {

    Flux<SearchResultDto> handle(Flux<Keyword> input);
}
