package com.zephyr.scraper.request.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.data.dto.QueryDto;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.helpers.Page;
import com.zephyr.scraper.request.headers.EngineSpecificHeadersProvider;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@ConditionalOnProperty(name = "scraper.bing.enabled", havingValue = "true")
public class BingRequestProvider extends AbstractRequestProvider {
    private static final String URL = "https://www.bing.com";
    private static final String URI = "/search";
    private static final String QUERY = "q";
    private static final String LANGUAGE = " language:";
    private static final String FIRST = "first";
    private static final String COUNT = "count";

    @Setter(onMethod = @__(@Autowired))
    private EngineSpecificHeadersProvider htmlHeadersProvider;

    public BingRequestProvider() {
        super(SearchEngine.BING);
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
                .put(QUERY, getQuery(query))
                .put(COUNT, page.getPageSize())
                .putIfTrue(FIRST, page.getStart(), page.isNotFirst())
                .build();
    }

    private String getQuery(QueryDto query) {
        return query.getQuery() + getLanguage(query);
    }

    private String getLanguage(QueryDto query) {
        return isNotBlank(query.getLanguageIso())
                ? LANGUAGE + query.getLanguageIso()
                : StringUtils.EMPTY;
    }
}
