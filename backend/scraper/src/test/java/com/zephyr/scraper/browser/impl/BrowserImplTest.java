package com.zephyr.scraper.browser.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zephyr.commons.interfaces.Manager;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.scraper.browser.provider.BrowsingProvider;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.configuration.properties.RequestType;
import com.zephyr.scraper.crawler.Crawler;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.exceptions.FraudException;
import com.zephyr.scraper.factories.SearchResultFactory;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Results;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class BrowserImplTest {

    @Mock
    private Crawler crawler;

    @Mock
    private SearchResultFactory searchResultFactory;

    @Mock
    private ScraperConfigurationService configurationService;

    @Mock
    private BrowsingProvider browsingProvider;

    @Mock
    private Manager<RequestType, BrowsingProvider> browsingManager;

    @InjectMocks
    private BrowserImpl testInstance;

    private final EngineRequest request = ScraperTestData.requests().google().firstPage();
    private final EngineResponse fraudResponse = ScraperTestData.responses().googleFraud();
    private final EngineResponse response = ScraperTestData.responses().google();
    private final SearchResultDto result = CommonTestData.searchResults().google();

    @Before
    public void setUp() {
        when(crawler.crawl(fraudResponse)).thenThrow(new FraudException(fraudResponse));
        when(crawler.crawl(response)).thenReturn(Results.GOOGLE_LINKS);
        when(searchResultFactory.create(request, Results.GOOGLE_LINKS)).thenReturn(result);
        when(configurationService.getRequestType(request.getProvider())).thenReturn(RequestType.DIRECT);
        when(browsingManager.manage(RequestType.DIRECT)).thenReturn(browsingProvider);
    }

    @Test
    public void shouldRetryOnFraudException() {
        when(browsingProvider.get(request))
                .thenReturn(Mono.just(fraudResponse))
                .thenReturn(Mono.just(response));

        StepVerifier.create(testInstance.get(request))
                .expectNext(result)
                .verifyComplete();

        verify(browsingProvider).onFail(fraudResponse);
    }
}