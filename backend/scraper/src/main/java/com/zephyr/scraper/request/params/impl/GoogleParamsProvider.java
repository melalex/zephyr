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
import java.util.Objects;

@Component
@RefreshScope
@ConditionalOnProperty(name = "scraper.google.enabled", havingValue = "true")
public class GoogleParamsProvider implements ParamsProvider {

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

    @Override
    public Map<String, List<String>> provide(QueryDto query, Page page) {
        return MultiValueMapBuilder.create()
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

    private String getParent(QueryDto queryDto) {
        return "g:" + queryDto.getPlace().getParent();
    }

    private String getLanguage(QueryDto query) {
        String iso = query.getLanguageIso();

        return Objects.nonNull(iso) ? "lang_" + query.getLanguageIso() : null;
    }
}