package com.zephyr.scraper.request.params.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zephyr.commons.support.Page;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.Query;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

@Tag("request")
class YandexParamsProviderTest {

    private YandexParamsProvider testInstance = new YandexParamsProvider();

    @Test
    void shouldProvideFirstPage() {
        Query query = ScraperTestData.queries().simple();
        Page page = ScraperTestData.pages().yandexFirstPage();

        Map<String, List<String>> expected = ScraperTestData.params().yandexFirstPage(query);

        Map<String, List<String>> actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }

    @Test
    void shouldProvideSecondPage() {
        Query query = ScraperTestData.queries().simple();
        Page page = ScraperTestData.pages().yandexSecondPage();

        Map<String, List<String>> expected = ScraperTestData.params().yandexSecondPage(query);

        Map<String, List<String>> actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }

}