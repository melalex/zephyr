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
public class YandexQueryProvider extends AbstractQueryProvider {
    private static final String URL = "https://yandex.ru";
    private static final String QUERY = "text";
    private static final String START = "b";
    private static final String COUNT = "n";

    @Value("${scraper.yandex.pageSize}")
    private int pageSize;

    public YandexQueryProvider() {
        super(SearchEngine.YANDEX);
    }

    @Override
    protected String provideBaseUrl(Task task) {
        return URL;
    }

    @Override
    protected Map<String, ?> providePage(Task task, int start) {
        return MapUtils.<String, Object>builder()
                .put(QUERY, task.getWord())
                .put(COUNT, pageSize)
                .putIfTrue(START, start, notFirstPage(start))
                .build();
    }
}
