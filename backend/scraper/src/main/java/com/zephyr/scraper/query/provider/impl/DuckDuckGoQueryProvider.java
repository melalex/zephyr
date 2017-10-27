package com.zephyr.scraper.query.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RefreshScope
public class DuckDuckGoQueryProvider extends AbstractQueryProvider {
    private static final String URL = "https://duckduckgo.com";
    private static final String QUERY = "q";
    private static final String SAFE = "kp";
    private static final String NOT_SAFE = "-2";
    private static final String AUTO_LOAD = "kc";
    private static final String NO_AUTO_LOAD = "1";

    @Value("${scraper.duckduckgo.pageSize}")
    private int pageSize;

    public DuckDuckGoQueryProvider() {
        super(SearchEngine.DUCKDUCKGO);
    }

    @Override
    protected String provideBaseUrl(Task task) {
        return URL;
    }

    // TODO: Improve me
    @Override
    protected Map<String, ?> providePage(Task task, int start) {
        return MapUtils.<String, Object>builder()
                .put(QUERY, task.getWord())
                .put(SAFE, NOT_SAFE)
                .put(AUTO_LOAD, NO_AUTO_LOAD)
                .build();
    }
}