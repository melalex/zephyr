package com.zephyr.scraper.query.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.source.CountrySource;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@RefreshScope
public class GoogleQueryProvider extends AbstractQueryProvider {
    private static final String DEFAULT_URL = "google.com";
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

    private static final int PAGE_SIZE = 100;

    @Setter(onMethod = @__(@Autowired))
    private CountrySource countrySource;

    public GoogleQueryProvider(@Value("scraper.google.enabled") boolean enabled,
                               @Value("${scraper.google.resultCount}") int count) {
        super(SearchEngine.GOOGLE, enabled, PAGE_SIZE, count);
    }

    @Override
    protected String provideUrl(Keyword keyword) {
        return "https://www." + getLocaleSearchEngine(keyword) + "/search";
    }

    @Override
    protected Map<String, ?> providePage(Keyword keyword, int page) {
        return MapUtils.<String, Object>builder()
                .put(SAFE, IMAGE)
                .put(AD_TEST, ON)
                .put(GLP, ONE)
                .put(QUERY, keyword.getWord())
                .put(NUMBER, PAGE_SIZE)
                .put(PARENT, getParent(keyword))
                .put(LOCATION, keyword.getLocation())
                .put(INTERFACE, keyword.getLanguageIso())
                .putIfTrue(START, page, notFirstPage(page))
                .putIfNotNull(LANGUAGE, getLanguage(keyword))
                .putIfTrueAndNotNull(COUNTRY, getCountry(keyword), keyword.isOnlyFromSpecifiedCountry())
                .build();
    }

    private String getParent(Keyword keyword) {
        return "g:" + keyword.getParent();
    }

    private String getCountry(Keyword keyword) {
        String iso = keyword.getCountryIso();

        return Objects.nonNull(iso) ? "country" + keyword.getCountryIso() : null;
    }

    private String getLanguage(Keyword keyword) {
        String iso = keyword.getLanguageIso();

        return Objects.nonNull(iso) ? "lang_" + keyword.getLanguageIso() : null;
    }

    private String getLocaleSearchEngine(Keyword keyword) {
        return Optional.ofNullable(countrySource.getByIso(keyword.getCountryIso()).getLocaleGoogle())
                .orElse(DEFAULT_URL);
    }
}