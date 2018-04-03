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
@ConditionalOnProperty(name = "scraper.yahoo.enabled", havingValue = "true")
public class YahooParamsProvider implements ParamsProvider {

    private static final String QUERY = "p";
    private static final String ENCODING = "ei";
    private static final String START = "b";
    private static final String COUNT = "n";
    private static final String UTF8 = "UTF-8";

    @Override
    public Map<String, List<String>> provide(QueryDto query, Page page) {
        return MultiValueMapBuilder.create()
                .put(QUERY, query.getQuery())
                .put(ENCODING, UTF8)
                .put(COUNT, page.getPageSize())
                .putIfTrue(START, page.getStart(), page.isNotFirst())
                .build();
    }
}