package com.zephyr.scraper.request.headers.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.Query;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class DefaultHeadersProviderTest {

    private DefaultHeadersProvider testInstance = new DefaultHeadersProvider();

    @Test
    public void shouldProvide() {
        Query query = ScraperTestData.queries().simple();

        Map<String, List<String>> expected = ScraperTestData.headers().defaultHeaders();
        Map<String, List<String>> actual = testInstance.provide(query, StringUtils.EMPTY);

        assertEquals(expected, actual);
    }
}