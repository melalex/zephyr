package com.zephyr.scraper.request.params.impl;

import com.zephyr.commons.support.MultiMapBuilder;
import com.zephyr.commons.support.Page;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.params.ParamsProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class YandexParamsProvider implements ParamsProvider {

    private static final String QUERY = "text";
    private static final String START = "b";
    private static final String COUNT = "n";

    @Override
    public Map<String, List<String>> provide(Query query, Page page) {
        return MultiMapBuilder.create()
                .put(QUERY, query.getQuery())
                .put(COUNT, page.getPageSize())
                .putIfTrue(START, page.getStart(), page.isNotFirst())
                .build();
    }
}