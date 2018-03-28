package com.zephyr.scraper.request.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.commons.support.Page;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.request.headers.EngineSpecificHeadersProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "scraper.google.enabled", havingValue = "true")
public class GoogleRequestProvider extends AbstractRequestProvider {
    private static final String URL = "https://www.google.com";
    private static final String URI = "/search";
    private static final String LANGUAGE = "lr";
    private static final String INTERFACE = "hl";
    private static final String AD_TEST = "adtest";
    private static final String ON = "on";
    private static final String LOCATION = "uule";
    private static final String GLP = "glp";
    private static final String ONE = "1";
    private static final String PARENT = "tci";
    private static final String SAFE = "safe";
    private static final String IMAGE = "image";
    private static final String QUERY = "q";
    private static final String START = "start";
    private static final String NUMBER = "num";

    @Setter(onMethod = @__(@Autowired))
    private EngineSpecificHeadersProvider htmlHeadersProvider;

    public GoogleRequestProvider() {
        super(SearchEngine.GOOGLE);
    }

    @Override
    protected String provideUrl(QueryDto query) {
        return getBaseUrl(query) + URI;
    }

    @Override
    protected Map<String, List<String>> provideHeaders(QueryDto query) {
        return htmlHeadersProvider.provide(getBaseUrl(query));
    }

    @Override
    protected Map<String, List<String>> provideParams(QueryDto query, Page page) {
        return MapUtils.multiValueMapBuilder()
                .put(SAFE, IMAGE)
                .put(AD_TEST, ON)
                .put(GLP, ONE)
                .put(QUERY, query.getQuery())
                .put(NUMBER, page.getPageSize())
                .put(PARENT, getParent(query))
                .put(LOCATION, query.getPlace().getUule())
                .putIfTrue(START, page.getStart(), page.isNotFirst())
                .putIfNotNull(INTERFACE, query.getLanguageIso())
                .putIfNotNull(LANGUAGE, getLanguage(query))
                .build();
    }

    private String getBaseUrl(QueryDto query) {
        return Optional.ofNullable(query.getPlace().getCountry().getLocaleGoogle()).orElse(URL);
    }

    private String getParent(QueryDto queryDto) {
        return "g:" + queryDto.getPlace().getParent();
    }

    private String getLanguage(QueryDto query) {
        String iso = query.getLanguageIso();

        return Objects.nonNull(iso) ? "lang_" + query.getLanguageIso() : null;
    }
}