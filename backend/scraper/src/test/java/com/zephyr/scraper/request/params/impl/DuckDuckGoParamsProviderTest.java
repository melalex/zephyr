package com.zephyr.scraper.request.params.impl;

import static org.junit.Assert.assertEquals;

import com.zephyr.commons.support.Page;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.Query;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class DuckDuckGoParamsProviderTest {

    private DuckDuckGoParamsProvider testInstance = new DuckDuckGoParamsProvider();

    @Test
    public void shouldProvide() {
        Query query = ScraperTestData.queries().simple();
        Page page = ScraperTestData.pages().duckDuckGoFirstPage();

        Map<String, List<String>> expected = ScraperTestData.params().duckDuckGoFirstPage(query);

        Map<String, List<String>> actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }
}