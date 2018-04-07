package com.zephyr.scraper.request.provider;

import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.Query;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface RequestProvider {

    static Function<RequestProvider, List<EngineRequest>> from(Query query) {
        return p -> p.provide(query);
    }

    List<EngineRequest> provide(Query query);
}
