package com.zephyr.scraper.request.headers.impl;

import static org.junit.Assert.assertEquals;

import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.request.agent.UserAgentProvider;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultHeadersProviderTest {

    @Mock
    private UserAgentProvider userAgentProvider;

    @InjectMocks
    private DefaultHeadersProvider testInstance;

    @Test
    public void shouldProvide() {
        var query = ScraperTestData.queries().simple();

        var expected = ScraperTestData.headers().defaultHeaders();
        var actual = testInstance.provide(query, StringUtils.EMPTY);

        assertEquals(expected, actual);
    }
}