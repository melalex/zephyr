package com.zephyr.scraper.request.params.impl;

import com.zephyr.commons.support.MultiValueMapBuilder;
import com.zephyr.commons.support.Page;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.scraper.request.params.ParamsProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RefreshScope
@ConditionalOnProperty(name = "scraper.duckduckgo.enabled", havingValue = "true")
public class DuckDuckGoParamsProvider implements ParamsProvider {

    private static final String QUERY = "q";
    private static final String SAFE = "kp";
    private static final String NOT_SAFE = "-2";
    private static final String AUTO_LOAD = "kc";
    private static final String NO_AUTO_LOAD = "1";

    @Override
    public Map<String, List<String>> provide(QueryDto query, Page page) {
        return MultiValueMapBuilder.create()
                .put(QUERY, query.getQuery())
                .put(SAFE, NOT_SAFE)
                .put(AUTO_LOAD, NO_AUTO_LOAD)
                .build();
    }
}