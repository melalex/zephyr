package com.zephyr.scraper.request;

import com.zephyr.data.dto.QueryDto;
import com.zephyr.scraper.domain.EngineRequest;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface RequestConstructor {

    Flux<EngineRequest> construct(QueryDto query);
}
