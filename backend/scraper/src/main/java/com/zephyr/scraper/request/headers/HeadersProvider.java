package com.zephyr.scraper.request.headers;

import com.zephyr.scraper.domain.Query;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@FunctionalInterface
public interface HeadersProvider {

    Map<String, List<String>> provide(Query query, String baseUrl);

    static Function<HeadersProvider, Map<String, List<String>>> from(Query query, String baseUrl) {
        return p -> p.provide(query, baseUrl);
    }
}
