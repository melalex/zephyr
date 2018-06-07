package com.zephyr.scraper.request.headers.impl;

import static org.junit.Assert.assertEquals;

import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.agent.UserAgentProvider;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class DefaultHeadersProviderTest {

    @Mock
    private UserAgentProvider userAgentProvider;

    @InjectMocks
    private DefaultHeadersProvider testInstance;

    @Test
    public void shouldProvide() {
        Query query = ScraperTestData.queries().simple();

        Map<String, List<String>> expected = ScraperTestData.headers().defaultHeaders();
        Map<String, List<String>> actual = testInstance.provide(query, StringUtils.EMPTY);

        assertEquals(expected, actual);
    }
}