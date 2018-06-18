package com.zephyr.scraper.request.params.impl;

import static org.junit.Assert.assertEquals;

import com.zephyr.scraper.data.ScraperTestData;
import org.junit.Test;

public class GoogleParamsProviderTest {

    private GoogleParamsProvider testInstance = new GoogleParamsProvider();

    @Test
    public void shouldProvideFirstPage() {
        var query = ScraperTestData.queries().simpleWithoutLanguage();
        var page = ScraperTestData.pages().googleFirstPage();

        var expected = ScraperTestData.params().googleNotLocalizedFirstPage(query);

        var actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldProvideSecondPageLocalized() {
        var query = ScraperTestData.queries().simple();
        var page = ScraperTestData.pages().googleSecondPage();

        var expected = ScraperTestData.params().googleSecondPage(query);

        var actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }
}