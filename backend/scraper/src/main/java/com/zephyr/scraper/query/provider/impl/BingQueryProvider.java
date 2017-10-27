package com.zephyr.scraper.query.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@RefreshScope
public class BingQueryProvider extends AbstractQueryProvider {
    private static final String URL = "https://www.bing.com";
    private static final String URI = "/search";
    private static final String QUERY = "q";
    private static final String LANGUAGE = " language:";
    private static final String FIRST = "first";
    private static final String COUNT = "count";

    @Value("${scraper.bing.pageSize}")
    private int pageSize;

    public BingQueryProvider() {
        super(SearchEngine.BING);
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
    protected Map<String, ?> providePage(Task task, int start) {
        int first = start + 1;

        return MapUtils.<String, Object>builder()
                .put(QUERY, getQuery(task))
                .put(COUNT, pageSize)
                .putIfTrue(FIRST, first, notFirstPage(start))
                .build();
    }

    private String getQuery(Task task) {
        return task.getWord() + getLanguage(task);
    }

    private String getLanguage(Task task) {
        return isNotBlank(task.getLanguageIso())
                ? LANGUAGE + task.getLanguageIso()
                : StringUtils.EMPTY;
    }
}
