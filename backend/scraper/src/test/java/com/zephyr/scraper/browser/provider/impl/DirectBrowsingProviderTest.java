package com.zephyr.scraper.browser.provider.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.data.ScraperTestProperties;
import com.zephyr.scraper.mocks.ConfigurationMock;
import com.zephyr.scraper.mocks.WebClientMock;
import com.zephyr.scraper.scheduling.SchedulingManager;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class DirectBrowsingProviderTest {

    private final DirectBrowsingProvider testInstance = new DirectBrowsingProvider();

    @Before
    public void setUp() {
        var webClient = WebClientMock.of();
        var configuration = ConfigurationMock.of();
        var schedulingManager = mock(SchedulingManager.class);

        when(schedulingManager.scheduleNext(SearchEngine.GOOGLE, ScraperTestProperties.DELAY))
                .thenReturn(Mono.empty());

        testInstance.setConfiguration(configuration);
        testInstance.setSchedulingManager(schedulingManager);
        testInstance.setWebClient(webClient);
    }

    @Test
    public void shouldGet() {
        var request = ScraperTestData.requests().google().firstPage();
        var response = ScraperTestData.responses().google();

        StepVerifier.create(testInstance.get(request))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    public void shouldRetryOnRequestException() {
        var failed = ScraperTestData.requests().failed();

        StepVerifier.withVirtualTime(() -> testInstance.get(failed))
                .expectSubscription()
                .expectNoEvent(ScraperTestProperties.BACKOFF)
                .expectNoEvent(ScraperTestProperties.BACKOFF)
                .expectNoEvent(ScraperTestProperties.BACKOFF)
                .expectNoEvent(ScraperTestProperties.DELAY)
                .expectNoEvent(ScraperTestProperties.BACKOFF)
                .expectNoEvent(ScraperTestProperties.BACKOFF)
                .expectNoEvent(ScraperTestProperties.BACKOFF)
                .thenCancel()
                .verify();
    }
}