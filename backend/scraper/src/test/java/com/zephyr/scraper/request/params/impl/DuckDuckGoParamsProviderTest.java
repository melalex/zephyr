package com.zephyr.scraper.request.params.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.zephyr.commons.support.Page;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.Query;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

@Tag("request")
class DuckDuckGoParamsProviderTest {

    private DuckDuckGoParamsProvider testInstance = new DuckDuckGoParamsProvider();

    @Test
    void shouldProvide() {
        Query query = ScraperTestData.queries().simple();
        Page page = ScraperTestData.pages().duckDuckGoFirstPage();

        Map<String, List<String>> expected = ScraperTestData.params().duckDuckGoFirstPage(query);

        Map<String, List<String>> actual = testInstance.provide(query, page);

        assertEquals(expected, actual);
    }
}