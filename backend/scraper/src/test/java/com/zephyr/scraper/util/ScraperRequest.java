package com.zephyr.scraper.util;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.Query;
import com.zephyr.test.Countries;
import com.zephyr.test.mocks.MockUidProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class ScraperRequest {

    public static final String GOOGLE_URI = "/search";
    public static final int FIRST_PAGE_OFFSET = 0;
    public static final int GOOGLE_SECOND_PAGE_OFFSET = 0;

    private ScraperQueries queries;
    private ScraperHeaders headers;
    private ScraperParams params;

    public EngineRequest googleFirstPage() {
        Query query = queries.simple();
        return googleBase(query).offset(FIRST_PAGE_OFFSET)
                .params(params.googleFirstPage(query))
                .build();
    }

    public EngineRequest googleSecondPage() {
        Query query = queries.simple();
        return googleBase(query).offset(GOOGLE_SECOND_PAGE_OFFSET)
                .params(params.googleSecondPage(query))
                .build();
    }

    private EngineRequest.EngineRequestBuilder googleBase(Query query) {
        return EngineRequest.builder()
                .id(MockUidProvider.DEFAULT_ID)
                .query(query)
                .provider(SearchEngine.GOOGLE)
                .url(Countries.UA_LOCALE_GOOGLE)
                .uri(GOOGLE_URI)
                .headers(headers.htmlHeadersFull(Countries.UA_LOCALE_GOOGLE));
    }
}
