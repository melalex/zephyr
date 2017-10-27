package com.zephyr.scraper.query.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.commons.PaginationUtils;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RefreshScope
public class YahooQueryProvider extends AbstractQueryProvider {
    private static final String URL = "https://search.yahoo.com";
    private static final String URI = "/search";
    private static final String QUERY = "p";
    private static final String ENCODING = "ei";
    private static final String START = "b";
    private static final String COUNT = "n";
    private static final String UTF8 = "UTF-8";

    public YahooQueryProvider() {
        super(SearchEngine.YAHOO);
    }

    @Override
    protected String provideBaseUrl(Task task) {
        return URL;
    }

    @Override
    protected String provideUri() {
        return URI;
    }

    @Override
    protected Map<String, ?> providePage(Task task, int page, int pageSize) {
        int first = PaginationUtils.startOf(page, pageSize);

        return MapUtils.<String, Object>builder()
                .put(QUERY, task.getWord())
                .put(ENCODING, UTF8)
                .put(COUNT, pageSize)
                .putIfTrue(START, first, PaginationUtils.isNotFirst(first))
                .build();
    }
}