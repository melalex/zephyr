package com.zephyr.scraper.request.params.impl;

import static org.junit.Assert.assertEquals;

import com.zephyr.commons.support.Page;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.Query;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class BingParamsProviderTest {

    private BingParamsProvider testInstance = new BingParamsProvider();

    @Test
    public void shouldProvideFirstPage() {
        Query query = ScraperTestData.queries().simpleWithoutLanguage();
        Page page = ScraperTestData.pages().bingFirstPage();

        Map<String, List<String>> expected = ScraperTestData.params().bingNotLocalizedFirstPage(query);

        Map<String, List<String>> actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldProvideSecondPageLocalized() {
        Query query = ScraperTestData.queries().simple();
        Page page = ScraperTestData.pages().bingSecondPage();

        Map<String, List<String>> expected = ScraperTestData.params().bingSecondPage(query);

        Map<String, List<String>> actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }
}