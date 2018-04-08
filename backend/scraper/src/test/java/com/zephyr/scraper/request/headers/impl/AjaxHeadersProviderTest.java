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
class AjaxHeadersProviderTest {

    private static final String BASE_URL = "BASE_URL";
    private static final String ACCEPT = "*/*";

    private AjaxHeadersProvider testInstance = new AjaxHeadersProvider();

    @Test
    void shouldProvide() {
        Map<String, List<String>> expected = Map.ofEntries(
                entry(HttpHeaders.ACCEPT, List.of(ACCEPT)),
                entry(HttpHeaders.HOST, List.of(BASE_URL)),
                entry(HttpHeaders.ORIGIN, List.of(BASE_URL))
        );

        Map<String, List<String>> actual = testInstance.provide(new Query(), BASE_URL);

        assertEquals(expected, actual);
    }
}