package com.zephyr.scraper.request.params.impl;

import static org.junit.Assert.assertEquals;

import com.zephyr.scraper.data.ScraperTestData;
import org.junit.Test;

public class YandexParamsProviderTest {

    private YandexParamsProvider testInstance = new YandexParamsProvider();

    @Test
    public void shouldProvideFirstPage() {
        var query = ScraperTestData.queries().simple();
        var page = ScraperTestData.pages().yandexFirstPage();

        var expected = ScraperTestData.params().yandexFirstPage(query);

        var actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldProvideSecondPage() {
        var query = ScraperTestData.queries().simple();
        var page = ScraperTestData.pages().yandexSecondPage();

        var expected = ScraperTestData.params().yandexSecondPage(query);

        var actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }

}