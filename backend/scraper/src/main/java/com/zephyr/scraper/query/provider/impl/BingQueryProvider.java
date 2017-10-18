package com.zephyr.scraper.query.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@RefreshScope
public class BingQueryProvider extends AbstractQueryProvider {
    private static final String URL = "https://www.bing.com/search";
    private static final String QUERY = "q";
    private static final String LANGUAGE = " language:";
    private static final String FIRST = "first";
    private static final String COUNT = "count";

    private static final int PAGE_SIZE = 100;

    public BingQueryProvider(@Value("scraper.bing.enabled") boolean enabled,
                             @Value("${scraper.bing.resultCount}") int count) {
        super(SearchEngine.BING, enabled, PAGE_SIZE, count);
    }

    @Value("${scraper.bing.resultCount}")
    private int count;

    @Override
    protected String provideUrl(Keyword keyword) {
        return URL;
    }

    @Override
    protected Map<String, ?> providePage(Keyword keyword, int start) {
        int first = start + 1;

        return MapUtils.<String, Object>builder()
                .put(QUERY, getQuery(keyword))
                .put(COUNT, PAGE_SIZE)
                .putIfTrue(FIRST, first, notFirstPage(start))
                .build();
    }

    private String getQuery(Keyword keyword) {
        return keyword.getWord() + getLanguage(keyword);
    }

    private String getLanguage(Keyword keyword) {
        return isNotBlank(keyword.getLanguageIso())
                ? LANGUAGE + keyword.getLanguageIso()
                : StringUtils.EMPTY;
    }
}
