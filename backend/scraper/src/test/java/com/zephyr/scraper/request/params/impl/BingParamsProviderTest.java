package com.zephyr.scraper.request.params.impl;

import static org.junit.Assert.assertEquals;

import com.zephyr.scraper.data.ScraperTestData;
import org.junit.Test;

public class BingParamsProviderTest {

    private BingParamsProvider testInstance = new BingParamsProvider();

    @Test
    public void shouldProvideFirstPage() {
        var query = ScraperTestData.queries().simpleWithoutLanguage();
        var page = ScraperTestData.pages().bingFirstPage();

        var expected = ScraperTestData.params().bingNotLocalizedFirstPage(query);

        var actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldProvideSecondPageLocalized() {
        var query = ScraperTestData.queries().simple();
        var page = ScraperTestData.pages().bingSecondPage();

        var expected = ScraperTestData.params().bingSecondPage(query);

        var actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }
}