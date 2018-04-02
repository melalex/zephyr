package com.zephyr.scraper.request.headers;

import com.zephyr.data.internal.dto.QueryDto;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@FunctionalInterface
public interface HeadersProvider {

    Map<String, List<String>> provide(QueryDto query, String baseUrl);

    static Function<HeadersProvider, Map<String, List<String>>> from(QueryDto query, String baseUrl) {
        return p -> p.provide(query, baseUrl);
    }
}
