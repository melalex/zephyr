package com.zephyr.scraper.request.headers.impl;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zephyr.scraper.domain.Query;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

@Tag("request")
class HtmlHeadersProviderTest {

    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String BASE_URL = "BASE_URL";
    private static final String TRUE = "1";

    private HtmlHeadersProvider testInstance = new HtmlHeadersProvider();

    @Test
    void shouldProvide() {
        Map<String, List<String>> expected = Map.ofEntries(
                entry(HttpHeaders.REFERER, List.of(ACCEPT)),
                entry(HttpHeaders.ACCEPT, List.of(BASE_URL)),
                entry(UPGRADE_INSECURE_REQUESTS, List.of(TRUE))
        );

        Map<String, List<String>> actual = testInstance.provide(new Query(), BASE_URL);

        assertEquals(expected, actual);
    }

}