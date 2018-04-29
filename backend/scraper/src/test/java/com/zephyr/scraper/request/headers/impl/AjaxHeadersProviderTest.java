package com.zephyr.scraper.request.headers.impl;

import static org.junit.Assert.assertEquals;

import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.Query;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class AjaxHeadersProviderTest {

    private static final String BASE_URL = "BASE_URL";

    private AjaxHeadersProvider testInstance = new AjaxHeadersProvider();

    @Test
    public void shouldProvide() {
        Map<String, List<String>> expected = ScraperTestData.headers().ajaxHeaders(BASE_URL);
        Map<String, List<String>> actual = testInstance.provide(new Query(), BASE_URL);

        assertEquals(expected, actual);
    }
}