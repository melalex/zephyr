package com.zephyr.scraper.request.headers.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.Query;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

@Tag("request")
class DefaultHeadersProviderTest {

    private static final String USER_AGENT = "USER_AGENT";
    private static final String ACCEPT_LANGUAGE = "ACCEPT_LANGUAGE";

    private DefaultHeadersProvider testInstance = new DefaultHeadersProvider();

    @Test
    void shouldProvide() {
        Query query = new Query();
        Query.UserAgent userAgent = new Query.UserAgent();
        userAgent.setHeader(USER_AGENT);
        query.setUserAgent(userAgent);
        query.setLanguageIso(ACCEPT_LANGUAGE);

        Map<String, List<String>> expected = ScraperTestData.headers().defaultHeaders(USER_AGENT);
        Map<String, List<String>> actual = testInstance.provide(query, StringUtils.EMPTY);

        assertEquals(expected, actual);
    }
}