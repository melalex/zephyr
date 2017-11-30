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

@Component
@ConditionalOnProperty(name = "scraper.yahoo.enabled", havingValue = "true")
public class YahooRequestProvider extends AbstractRequestProvider {
    private static final String URL = "https://search.yahoo.com";
    private static final String URI = "/search";
    private static final String QUERY = "p";
    private static final String ENCODING = "ei";
    private static final String START = "b";
    private static final String COUNT = "n";
    private static final String UTF8 = "UTF-8";

    @Setter(onMethod = @__(@Autowired))
    private EngineSpecificHeadersProvider htmlHeadersProvider;

    public YahooRequestProvider() {
        super(SearchEngine.YAHOO);
    }

    @Override
    protected String provideUrl(QueryDto query) {
        return URL + URI;
    }

    @Override
    protected Map<String, List<String>> provideHeaders(QueryDto query) {
        return htmlHeadersProvider.provide(URL);
    }

    @Override
    protected Map<String, List<String>> provideParams(QueryDto query, Page page) {
        return MapUtils.multiValueMapBuilder()
                .put(QUERY, query.getQuery())
                .put(ENCODING, UTF8)
                .put(COUNT, page.getPageSize())
                .putIfTrue(START, page.getStart(), page.isNotFirst())
                .build();
    }
}