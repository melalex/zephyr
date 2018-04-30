package com.zephyr.scraper.browser.provider.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.data.ScraperTestProperties;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.mocks.ConfigurationMock;
import com.zephyr.scraper.mocks.WebClientMock;
import com.zephyr.scraper.scheduling.SchedulingManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class DirectBrowsingProviderTest {

    private final DirectBrowsingProvider testInstance = new DirectBrowsingProvider();

    @Before
    public void setUp() {
        WebClient webClient = WebClientMock.of();
        ScraperConfigurationService configuration = ConfigurationMock.of();
        SchedulingManager schedulingManager = mock(SchedulingManager.class);

        when(schedulingManager.scheduleNext(SearchEngine.GOOGLE, ScraperTestProperties.DELAY))
                .thenReturn(Mono.empty());

        testInstance.setConfiguration(configuration);
        testInstance.setSchedulingManager(schedulingManager);
        testInstance.setWebClient(webClient);
    }

    @Test
    public void shouldGet() {
        EngineRequest request = ScraperTestData.requests().google().firstPage();
        EngineResponse response = ScraperTestData.responses().google();

        StepVerifier.create(testInstance.get(request))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    public void shouldRetryOnRequestException() {
        EngineRequest failed = ScraperTestData.requests().failed();

        StepVerifier.create(testInstance.get(failed))
                .expectNoEvent(ScraperTestProperties.BACKOFF)
                .expectNoEvent(ScraperTestProperties.BACKOFF)
                .expectNoEvent(ScraperTestProperties.BACKOFF)
                .verifyErrorMatches(ScraperTestData.responses().failed()::equals);
    }
}