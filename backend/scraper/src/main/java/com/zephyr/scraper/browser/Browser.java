package com.zephyr.scraper.browser;

import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.scraper.domain.EngineRequest;
import reactor.core.publisher.Mono;

public interface Browser {

    Mono<SearchResultDto> get(EngineRequest engineRequest);
}