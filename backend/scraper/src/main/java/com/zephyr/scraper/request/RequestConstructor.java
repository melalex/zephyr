package com.zephyr.scraper.request;

import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.Query;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface RequestConstructor {

    Flux<EngineRequest> construct(Query query);
}
