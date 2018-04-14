package com.zephyr.scraper.request.provider.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.zephyr.commons.support.Page;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.data.ScraperRequests;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.headers.HeadersProvider;
import com.zephyr.scraper.request.params.ParamsProvider;
import com.zephyr.scraper.request.url.UrlProvider;
import com.zephyr.test.extensions.MockitoExtension;
import com.zephyr.test.mocks.UidProviderMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.List;

@Tag("request")
@ExtendWith(MockitoExtension.class)
class DefaultRequestProviderTest {

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


    @BeforeEach
    void setUp() {
        testInstance = DefaultRequestProvider.builder()
                .engine(SEARCH_ENGINE)
                .configuration(configuration)
                .uidProvider(UidProviderMock.of())
                .urlProvider(urlProvider)
                .paramsProvider(paramsProvider)
                .headersProviders(List.of(defaultHeadersProvider, htmlHeadersProvider))
                .build();

        Page firstPage = ScraperTestData.pages().googleFirstPage();
        Page secondPage = ScraperTestData.pages().googleSecondPage();
        String baseUrl = query.getPlace().getCountry().getLocaleGoogle();

        when(configuration.getFirstPage(SEARCH_ENGINE)).thenReturn(firstPage);
        when(htmlHeadersProvider.provide(query, baseUrl)).thenReturn(ScraperTestData.headers().htmlHeaders(baseUrl));
        when(defaultHeadersProvider.provide(query, baseUrl)).thenReturn(ScraperTestData.headers().defaultHeaders());
        when(urlProvider.provideBaseUrl(query)).thenReturn(baseUrl);
        when(urlProvider.provideUri(query)).thenReturn(URI);
        when(paramsProvider.provide(query, firstPage)).thenReturn(ScraperTestData.params().googleFirstPage(query));
        when(paramsProvider.provide(query, secondPage)).thenReturn(ScraperTestData.params().googleSecondPage(query));
    }

    @Test
    void shouldProvide() {
        List<EngineRequest> expected = List.of(
                ScraperTestData.requests().google().firstPage(query),
                ScraperTestData.requests().google().secondPage(query)
        );

        List<EngineRequest> actual = testInstance.provide(query);

        assertEquals(expected, actual);
    }
}