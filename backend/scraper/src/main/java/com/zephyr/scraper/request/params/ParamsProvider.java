package com.zephyr.scraper.request.params;

import com.zephyr.commons.support.Page;
import com.zephyr.data.internal.dto.QueryDto;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@FunctionalInterface
public interface ParamsProvider {

    Map<String, List<String>> provide(QueryDto query, Page page);

    static Function<ParamsProvider, Map<String, List<String>>> from(QueryDto query, Page page) {
        return p -> p.provide(query, page);
    }
}
