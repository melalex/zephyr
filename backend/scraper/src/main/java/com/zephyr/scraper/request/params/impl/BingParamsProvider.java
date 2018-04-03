package com.zephyr.scraper.request.params.impl;

import com.zephyr.commons.support.MultiValueMapBuilder;
import com.zephyr.commons.support.Page;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.scraper.request.params.ParamsProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RefreshScope
@ConditionalOnProperty(name = "scraper.bing.enabled", havingValue = "true")
public class BingParamsProvider implements ParamsProvider {

    private static final String QUERY = "q";
    private static final String LANGUAGE_FORMAT = " language:%s";
    private static final String FIRST = "first";
    private static final String COUNT = "count";

    @Override
    public Map<String, List<String>> provide(QueryDto query, Page page) {
        return MultiValueMapBuilder.create()
                .put(QUERY, getQuery(query))
                .put(COUNT, page.getPageSize())
                .putIfTrue(FIRST, page.getStart(), page.isNotFirst())
                .build();
    }

    private String getQuery(QueryDto query) {
        return query.getQuery() + getLanguage(query);
    }

    private String getLanguage(QueryDto query) {
        return Optional.ofNullable(query.getLanguageIso())
                .map(l -> String.format(LANGUAGE_FORMAT, l))
                .orElse(StringUtils.EMPTY);
    }
}
