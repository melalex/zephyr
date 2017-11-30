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
@ConditionalOnProperty(name = "scraper.duckduckgo.enabled", havingValue = "true")
public class DuckDuckGoRequestProvider extends AbstractRequestProvider {
    private static final String URL = "https://duckduckgo.com";
    private static final String QUERY = "q";
    private static final String SAFE = "kp";
    private static final String NOT_SAFE = "-2";
    private static final String AUTO_LOAD = "kc";
    private static final String NO_AUTO_LOAD = "1";

    @Setter(onMethod = @__(@Autowired))
    private EngineSpecificHeadersProvider ajaxHeadersProvider;

    public DuckDuckGoRequestProvider() {
        super(SearchEngine.DUCKDUCKGO);
    }

    @Override
    protected String provideUrl(QueryDto query) {
        return URL;
    }

    @Override
    protected Map<String, List<String>> provideHeaders(QueryDto query) {
        return ajaxHeadersProvider.provide(URL);
    }

    @Override
    protected Map<String, List<String>> provideParams(QueryDto query, Page page) {
        return MapUtils.multiValueMapBuilder()
                .put(QUERY, query.getQuery())
                .put(SAFE, NOT_SAFE)
                .put(AUTO_LOAD, NO_AUTO_LOAD)
                .build();
    }
}