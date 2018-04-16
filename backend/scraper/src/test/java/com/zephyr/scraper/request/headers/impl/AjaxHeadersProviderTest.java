package com.zephyr.scraper.request.headers.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.Query;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

@Tags({@Tag("request"), @Tag("unit")})
class AjaxHeadersProviderTest {

    private static final String BASE_URL = "BASE_URL";

    private AjaxHeadersProvider testInstance = new AjaxHeadersProvider();

    @Test
    void shouldProvide() {
        Map<String, List<String>> expected = ScraperTestData.headers().ajaxHeaders(BASE_URL);
        Map<String, List<String>> actual = testInstance.provide(new Query(), BASE_URL);

        assertEquals(expected, actual);
    }
}