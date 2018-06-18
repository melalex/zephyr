package com.zephyr.scraper.request.headers.impl;

import static org.junit.Assert.assertEquals;

import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.Query;
import org.junit.Test;

public class HtmlHeadersProviderTest {

    private static final String BASE_URL = "BASE_URL";

    private HtmlHeadersProvider testInstance = new HtmlHeadersProvider();

    @Test
    public void shouldProvide() {
        var expected = ScraperTestData.headers().htmlHeaders(BASE_URL);
        var actual = testInstance.provide(new Query(), BASE_URL);

        assertEquals(expected, actual);
    }
}