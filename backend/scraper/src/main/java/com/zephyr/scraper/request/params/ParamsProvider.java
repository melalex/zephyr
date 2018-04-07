package com.zephyr.scraper.request.params;

import com.zephyr.commons.support.Page;
import com.zephyr.scraper.domain.Query;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@FunctionalInterface
public interface ParamsProvider {

    static Function<ParamsProvider, Map<String, List<String>>> from(Query query, Page page) {
        return p -> p.provide(query, page);
    }

    Map<String, List<String>> provide(Query query, Page page);
}
