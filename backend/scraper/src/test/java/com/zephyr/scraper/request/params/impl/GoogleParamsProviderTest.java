package com.zephyr.scraper.request.params.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zephyr.commons.support.Page;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.Query;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

@Tags({@Tag("request"), @Tag("unit")})
class GoogleParamsProviderTest {

    private GoogleParamsProvider testInstance = new GoogleParamsProvider();

    @Test
    void shouldProvideFirstPage() {
        Query query = ScraperTestData.queries().simpleWithoutLanguage();
        Page page = ScraperTestData.pages().googleFirstPage();

        Map<String, List<String>> expected = ScraperTestData.params().googleNotLocalizedFirstPage(query);

        Map<String, List<String>> actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }

    @Test
    void shouldProvideSecondPageLocalized() {
        Query query = ScraperTestData.queries().simple();
        Page page = ScraperTestData.pages().googleSecondPage();

        Map<String, List<String>> expected = ScraperTestData.params().googleSecondPage(query);

        Map<String, List<String>> actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }
}