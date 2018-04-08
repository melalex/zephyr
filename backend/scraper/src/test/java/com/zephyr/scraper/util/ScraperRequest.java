package com.zephyr.scraper.util;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.test.Countries;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class ScraperRequest {

    public static final String GOOGLE_URI = "";
    public static final String GOOGLE_FIRST_PAGE_ID = UUID.randomUUID().toString();
    public static final int FIRST_PAGE_OFFSET = 0;

    private ScraperQueries queries;

    private EngineRequest googleFirstPage() {
        return google().id(GOOGLE_FIRST_PAGE_ID)
                .offset(FIRST_PAGE_OFFSET)
                .headers(googleHeaders())
                .params(googleParams())
                .build();
    }

    private EngineRequest googleSecondPage() {
        return google().id(GOOGLE_FIRST_PAGE_ID)
                .offset(FIRST_PAGE_OFFSET)
                .headers(googleHeaders())
                .params(googleParams())
                .build();
    }

    private EngineRequest.EngineRequestBuilder google() {
        return EngineRequest.builder()
                .query(queries.simple())
                .provider(SearchEngine.GOOGLE)
                .url(Countries.UA_LOCALE_GOOGLE)
                .uri(GOOGLE_URI);
    }

    private Map<String, List<String>> googleParams() {
        return null;
    }

    private Map<String, List<String>> googleHeaders() {
        return null;
    }
}
