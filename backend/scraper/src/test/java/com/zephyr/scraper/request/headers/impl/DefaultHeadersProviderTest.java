package com.zephyr.scraper.request.headers.impl;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zephyr.scraper.domain.Query;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

@Tag("request")
class DefaultHeadersProviderTest {

    private static final String DO_NOT_TRACK = "DNT";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";
    private static final String USER_AGENT = "USER_AGENT";
    private static final String ACCEPT_LANGUAGE = "ACCEPT_LANGUAGE";

    private DefaultHeadersProvider testInstance = new DefaultHeadersProvider();

    @Test
    void shouldProvide() {
        Map<String, List<String>> expected = Map.ofEntries(
                entry(HttpHeaders.USER_AGENT, List.of(USER_AGENT)),
                entry(HttpHeaders.ACCEPT_LANGUAGE, List.of(ACCEPT_LANGUAGE)),
                entry(HttpHeaders.ACCEPT_ENCODING, List.of(ENCODING)),
                entry(HttpHeaders.CONNECTION, List.of(KEEP_ALIVE)),
                entry(DO_NOT_TRACK, List.of(TRUE))
        );

        Query query = new Query();
        Query.UserAgent userAgent = new Query.UserAgent();
        userAgent.setHeader(USER_AGENT);
        query.setUserAgent(userAgent);
        query.setLanguageIso(ACCEPT_LANGUAGE);

        Map<String, List<String>> actual = testInstance.provide(query, StringUtils.EMPTY);

        assertEquals(expected, actual);
    }
}