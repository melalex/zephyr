package com.zephyr.scraper.query;

import com.zephyr.data.commons.Keyword;
import com.zephyr.scraper.domain.EngineRequest;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface QueryConstructor {

    Flux<EngineRequest> construct(Keyword keyword);
}
