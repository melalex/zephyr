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
@ConditionalOnProperty(name = "scraper.yandex.enabled", havingValue = "true")
public class YandexParamsProvider implements ParamsProvider {

    private static final String QUERY = "text";
    private static final String START = "b";
    private static final String COUNT = "n";

    @Override
    public Map<String, List<String>> provide(QueryDto query, Page page) {
        return MultiValueMapBuilder.create()
                .put(QUERY, query.getQuery())
                .put(COUNT, page.getPageSize())
                .putIfTrue(START, page.getStart(), page.isNotFirst())
                .build();
    }
}