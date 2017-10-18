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
public class YahooQueryProvider extends AbstractQueryProvider {
    private static final String URL = "https://search.yahoo.com/search";
    private static final String QUERY = "p";
    private static final String ENCODING = "ei";
    private static final String START = "b";
    private static final String COUNT = "n";
    private static final String UTF8 = "UTF-8";

    private static final int PAGE_SIZE = 10;

    public YahooQueryProvider(@Value("scraper.yahoo.enabled") boolean enabled,
                              @Value("${scraper.yahoo.resultCount}") int count) {
        super(SearchEngine.YAHOO, enabled, PAGE_SIZE, count);
    }

    @Override
    protected String provideUrl(Keyword keyword) {
        return URL;
    }

    @Override
    protected Map<String, ?> providePage(Keyword keyword, int start) {
        int first = start + 1;

        return MapUtils.<String, Object>builder()
                .put(QUERY, keyword.getWord())
                .put(ENCODING, UTF8)
                .put(COUNT, PAGE_SIZE)
                .putIfTrue(START, first, notFirstPage(start))
                .build();
    }
}