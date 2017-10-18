package com.zephyr.scraper.query.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.commons.PaginationUtils;
import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.source.CountrySource;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RefreshScope
public class GoogleQueryProvider extends AbstractQueryProvider {
    private static final String DEFAULT_URL = "google.com";
    private static final String LANGUAGE = "lr";
    private static final String COUNTRY = "cr";
    private static final String AD_TEST = "adtest";
    private static final String ON = "on";
    private static final String LOCATION = "uule";
    private static final String SAFE = "safe";
    private static final String IMAGE = "image";
    private static final String QUERY = "q";
    private static final String START = "start";
    private static final String NUMBER = "num";

    private static final int MAX_NUMBER_PER_PAGE = 100;

    @Setter(onMethod = @__(@Autowired))
    private CountrySource countrySource;

    @Value("${scraper.google.resultCount}")
    private int count;

    public GoogleQueryProvider(@Value("scraper.google.enabled") boolean enabled) {
        super(SearchEngine.GOOGLE, enabled);
    }

    @Override
    protected String provideUrl(Keyword keyword) {
        return "https://www." + getLocaleSearchEngine(keyword) + "/search";
    }

    @Override
    protected List<Map<String, ?>> providePages(Keyword keyword) {
        return PaginationUtils.pagesStream(count, MAX_NUMBER_PER_PAGE)
                .map(p -> getParams(keyword, p))
                .collect(Collectors.toList());
    }

    private Map<String, ?> getParams(Keyword keyword, int page) {
        int start = PaginationUtils.startOf(page, MAX_NUMBER_PER_PAGE);
        boolean notFirstPage = start != 0;

        return MapUtils.<String, Object>builder()
                .put(SAFE, IMAGE)
                .put(AD_TEST, ON)
                .put(QUERY, keyword.getWord())
                .put(NUMBER, MAX_NUMBER_PER_PAGE)
                .put(LOCATION, getLocation(keyword))
                .putIfTrue(START, start, notFirstPage)
                .putIfNotNull(LANGUAGE, getLanguage(keyword))
                .putIfTrueAndNotNull(COUNTRY, getCountry(keyword), keyword.isOnlyFromSpecifiedCountry())
                .build();
    }

    private String getCountry(Keyword keyword) {
        String iso = keyword.getCountryIso();

        return Objects.nonNull(iso) ? "country" + keyword.getCountryIso() : null;
    }

    private String getLanguage(Keyword keyword) {
        String iso = keyword.getLanguageIso();

        return Objects.nonNull(iso) ? "lang_" + keyword.getLanguageIso() : null;
    }

    private String getLocation(Keyword keyword) {
        return null;
    }

    private String getLocaleSearchEngine(Keyword keyword) {
        return Optional.ofNullable(countrySource.getByIso(keyword.getCountryIso()).getLocaleGoogle())
                .orElse(DEFAULT_URL);
    }
}