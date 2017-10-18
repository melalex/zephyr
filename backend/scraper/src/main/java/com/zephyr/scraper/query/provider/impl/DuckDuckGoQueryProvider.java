package com.zephyr.scraper.query.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RefreshScope
public class DuckDuckGoQueryProvider extends AbstractQueryProvider {
    private static final String URL = "https://www.bing.com/search";
    private static final String QUERY = "q";
    private static final String SAFE = "kp";
    private static final String NOT_SAFE = "-2";
    private static final String AUTO_LOAD = "kc";
    private static final String NO_AUTO_LOAD = "1";

    private static final int PAGE_SIZE = 10;

    public DuckDuckGoQueryProvider(@Value("scraper.duckduckgo.enabled") boolean enabled,
                                   @Value("${scraper.duckduckgo.resultCount}") int count) {
        super(SearchEngine.DUCKDUCKGO, enabled, PAGE_SIZE, count);
    }

    @Override
    protected String provideUrl(Keyword keyword) {
        return URL;
    }

    // TODO: Improve me
    @Override
    protected Map<String, ?> providePage(Keyword keyword, int start) {
        return MapUtils.<String, Object>builder()
                .put(QUERY, keyword.getWord())
                .put(SAFE, NOT_SAFE)
                .put(AUTO_LOAD, NO_AUTO_LOAD)
                .build();
    }
}