package com.zephyr.scraper.request.params.impl;

import com.zephyr.commons.support.MultiMapBuilder;
import com.zephyr.commons.support.Page;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.params.ParamsProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DuckDuckGoParamsProvider implements ParamsProvider {

    private static final String QUERY = "q";
    private static final String SAFE = "kp";
    private static final String NOT_SAFE = "-2";
    private static final String AUTO_LOAD = "kc";
    private static final String NO_AUTO_LOAD = "1";

    @Override
    public Map<String, List<String>> provide(Query query, Page page) {
        return MultiMapBuilder.create()
                .put(QUERY, query.getQuery())
                .put(SAFE, NOT_SAFE)
                .put(AUTO_LOAD, NO_AUTO_LOAD)
                .build();
    }
}