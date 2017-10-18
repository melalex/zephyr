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
public class YandexQueryProvider extends AbstractQueryProvider {
    private static final String URL = "https://yandex.ru";
    private static final String QUERY = "text";
    private static final String START = "b";
    private static final String COUNT = "n";

    private static final int PAGE_SIZE = 10;

    public YandexQueryProvider(@Value("scraper.yandex.enabled") boolean enabled,
                               @Value("scraper.yandex.resultCount}") int count) {
        super(SearchEngine.YANDEX, enabled, PAGE_SIZE, count);
    }

    @Override
    protected String provideUrl(Keyword keyword) {
        return URL;
    }

    @Override
    protected Map<String, ?> providePage(Keyword keyword, int start) {
        return MapUtils.<String, Object>builder()
                .put(QUERY, keyword.getWord())
                .put(COUNT, PAGE_SIZE)
                .putIfTrue(START, start, notFirstPage(start))
                .build();
    }
}
