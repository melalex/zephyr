package com.zephyr.scraper.query.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@RefreshScope
public class GoogleQueryProvider extends AbstractQueryProvider {
    private static final String URI = "/search";
    private static final String LANGUAGE = "lr";
    private static final String INTERFACE = "hl";
    private static final String COUNTRY = "cr";
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

    @Value("${scraper.google.pageSize}")
    private int pageSize;

    public GoogleQueryProvider() {
        super(SearchEngine.GOOGLE);
    }

    @Override
    protected String provideBaseUrl(Task task) {
        return "https://www." + task.getLocaleGoogle();
    }

    @Override
    protected String provideUri() {
        return URI;
    }

    @Override
    protected Map<String, ?> providePage(Task task, int page) {
        return MapUtils.<String, Object>builder()
                .put(SAFE, IMAGE)
                .put(AD_TEST, ON)
                .put(GLP, ONE)
                .put(QUERY, task.getWord())
                .put(NUMBER, pageSize)
                .put(PARENT, getParent(task))
                .put(LOCATION, task.getLocation())
                .put(INTERFACE, task.getLanguageIso())
                .putIfTrue(START, page, notFirstPage(page))
                .putIfNotNull(LANGUAGE, getLanguage(task))
                .putIfTrueAndNotNull(COUNTRY, getCountry(task), task.isOnlyFromSpecifiedCountry())
                .build();
    }

    private String getParent(Task task) {
        return "g:" + task.getParent();
    }

    private String getCountry(Task task) {
        String iso = task.getCountryIso();

        return Objects.nonNull(iso) ? "country" + task.getCountryIso() : null;
    }

    private String getLanguage(Task task) {
        String iso = task.getLanguageIso();

        return Objects.nonNull(iso) ? "lang_" + task.getLanguageIso() : null;
    }
}