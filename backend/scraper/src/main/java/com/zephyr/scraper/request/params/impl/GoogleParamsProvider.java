package com.zephyr.scraper.request.params.impl;

import com.zephyr.commons.StreamUtils;
import com.zephyr.commons.support.MultiMapBuilder;
import com.zephyr.commons.support.Page;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.params.ParamsProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
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
    private static final String LANGUAGE_TEMPLATE = "lang_%s";
    private static final String PARENT_TEMPLATE = "p:%s";

    @Override
    public Map<String, List<String>> provide(Query query, Page page) {
        return MultiMapBuilder.create()
                .put(SAFE, IMAGE)
                .put(AD_TEST, ON)
                .put(GLP, ONE)
                .put(QUERY, query.getQuery())
                .put(NUMBER, page.getPageSize())
                .put(PARENT, String.format(PARENT_TEMPLATE, query.getPlace().getParent()))
                .put(LOCATION, query.getPlace().getUule())
                .putIfTrue(START, page.getStart(), page.isNotFirst())
                .putIfNotNull(INTERFACE, query.getLanguageIso())
                .putIfNotNull(LANGUAGE, getLanguage(query))
                .build();
    }

    private String getLanguage(Query query) {
        return Optional.ofNullable(query.getLanguageIso())
               .map(StreamUtils.format(LANGUAGE_TEMPLATE))
               .orElse(null);
    }
}