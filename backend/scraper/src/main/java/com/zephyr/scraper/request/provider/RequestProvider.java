package com.zephyr.scraper.request.provider;

import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.scraper.domain.EngineRequest;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface RequestProvider {

    List<EngineRequest> provide(QueryDto query);

    static Function<RequestProvider, List<EngineRequest>> from(QueryDto query) {
        return p -> p.provide(query);
    }
}
