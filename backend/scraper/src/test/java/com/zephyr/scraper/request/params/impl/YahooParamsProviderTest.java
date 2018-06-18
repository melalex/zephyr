package com.zephyr.scraper.request.params.impl;

import static org.junit.Assert.assertEquals;

import com.zephyr.scraper.data.ScraperTestData;
import org.junit.Test;

public class YahooParamsProviderTest {

    private YahooParamsProvider testInstance = new YahooParamsProvider();

    @Test
    public void shouldProvideFirstPage() {
        var query = ScraperTestData.queries().simple();
        var page = ScraperTestData.pages().yahooFirstPage();

        var expected = ScraperTestData.params().yahooFirstPage(query);

        var actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldProvideSecondPage() {
        var query = ScraperTestData.queries().simple();
        var page = ScraperTestData.pages().yahooSecondPage();

        var expected = ScraperTestData.params().yahooSecondPage(query);

        var actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }
}