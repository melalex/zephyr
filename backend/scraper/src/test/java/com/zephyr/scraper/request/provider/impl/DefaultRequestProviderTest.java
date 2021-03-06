package com.zephyr.scraper.request.provider.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.data.ScraperRequests;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.headers.HeadersProvider;
import com.zephyr.scraper.request.params.ParamsProvider;
import com.zephyr.scraper.request.url.UrlProvider;
import com.zephyr.test.mocks.UidProviderMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRequestProviderTest {

    private static final SearchEngine SEARCH_ENGINE = SearchEngine.GOOGLE;
    private static final String URI = ScraperRequests.GOOGLE_URI;

    private Query query = ScraperTestData.queries().simple();

    @Mock
    private ParamsProvider paramsProvider;

    @Mock
    private UrlProvider urlProvider;

    @Mock
    private ScraperConfigurationService configuration;

    @Mock
    private HeadersProvider htmlHeadersProvider;

    @Mock
    private HeadersProvider defaultHeadersProvider;

    private DefaultRequestProvider testInstance;

    @Before
    public void setUp() {
        testInstance = DefaultRequestProvider.builder()
                .engine(SEARCH_ENGINE)
                .configuration(configuration)
                .uidProvider(UidProviderMock.of())
                .urlProvider(urlProvider)
                .paramsProvider(paramsProvider)
                .headersProviders(List.of(defaultHeadersProvider, htmlHeadersProvider))
                .build();

        var firstPage = ScraperTestData.pages().googleFirstPage();
        var secondPage = ScraperTestData.pages().googleSecondPage();
        var baseUrl = query.getPlace().getCountry().getLocaleGoogle();

        when(configuration.getFirstPage(SEARCH_ENGINE)).thenReturn(firstPage);
        when(htmlHeadersProvider.provide(query, baseUrl)).thenReturn(ScraperTestData.headers().htmlHeaders(baseUrl));
        when(defaultHeadersProvider.provide(query, baseUrl)).thenReturn(ScraperTestData.headers().defaultHeaders());
        when(urlProvider.provideBaseUrl(query)).thenReturn(baseUrl);
        when(urlProvider.provideUri(query)).thenReturn(URI);
        when(paramsProvider.provide(query, firstPage)).thenReturn(ScraperTestData.params().googleFirstPage(query));
        when(paramsProvider.provide(query, secondPage)).thenReturn(ScraperTestData.params().googleSecondPage(query));
    }

    @Test
    public void shouldProvide() {
        var expected = List.of(
                ScraperTestData.requests().google().firstPage(query),
                ScraperTestData.requests().google().secondPage(query)
        );

        var actual = testInstance.provide(query);

        assertEquals(expected, actual);
    }
}