package com.zephyr.scraper.request.params.impl;

import static org.junit.Assert.assertEquals;

import com.zephyr.scraper.data.ScraperTestData;
import org.junit.Test;

public class DuckDuckGoParamsProviderTest {

    private DuckDuckGoParamsProvider testInstance = new DuckDuckGoParamsProvider();

    @Test
    public void shouldProvide() {
        var query = ScraperTestData.queries().simple();
        var page = ScraperTestData.pages().duckDuckGoFirstPage();

        var expected = ScraperTestData.params().duckDuckGoFirstPage(query);

        var actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }
}