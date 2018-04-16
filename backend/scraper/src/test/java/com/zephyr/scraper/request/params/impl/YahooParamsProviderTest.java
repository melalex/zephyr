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
class YahooParamsProviderTest {

    private YahooParamsProvider testInstance = new YahooParamsProvider();

    @Test
    void shouldProvideFirstPage() {
        Query query = ScraperTestData.queries().simple();
        Page page = ScraperTestData.pages().yahooFirstPage();

        Map<String, List<String>> expected = ScraperTestData.params().yahooFirstPage(query);

        Map<String, List<String>> actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }

    @Test
    void shouldProvideSecondPage() {
        Query query = ScraperTestData.queries().simple();
        Page page = ScraperTestData.pages().yahooSecondPage();

        Map<String, List<String>> expected = ScraperTestData.params().yahooSecondPage(query);

        Map<String, List<String>> actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }
}