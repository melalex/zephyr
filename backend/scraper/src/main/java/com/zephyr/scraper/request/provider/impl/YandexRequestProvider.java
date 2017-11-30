package com.zephyr.scraper.request.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.data.dto.QueryDto;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.helpers.Page;
import com.zephyr.scraper.request.headers.EngineSpecificHeadersProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "scraper.yandex.enabled", havingValue = "true")
public class YandexRequestProvider extends AbstractRequestProvider {
    private static final String URL = "https://yandex.ru";
    private static final String QUERY = "text";
    private static final String START = "b";
    private static final String COUNT = "n";

    @Setter(onMethod = @__(@Autowired))
    private EngineSpecificHeadersProvider ajaxHeadersProvider;

    public YandexRequestProvider() {
        super(SearchEngine.YANDEX);
    }

    @Override
    protected String provideUrl(QueryDto query) {
        return Optional.ofNullable(query.getPlace().getCountry().getLocaleYandex()).orElse(URL);
    }

    @Override
    protected Map<String, List<String>> provideHeaders(QueryDto query) {
        return ajaxHeadersProvider.provide(provideUrl(query));
    }

    @Override
    protected Map<String, List<String>> provideParams(QueryDto query, Page page) {
        return MapUtils.multiValueMapBuilder()
                .put(QUERY, query.getQuery())
                .put(COUNT, page.getPageSize())
                .putIfTrue(START, page.getStart(), page.isNotFirst())
                .build();
    }
}