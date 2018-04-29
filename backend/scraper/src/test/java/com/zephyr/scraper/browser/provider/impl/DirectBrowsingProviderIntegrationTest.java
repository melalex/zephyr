package com.zephyr.scraper.browser.provider.impl;

import com.zephyr.scraper.ScraperTestConfiguration;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
@Import(ScraperTestConfiguration.class)
public class DirectBrowsingProviderIntegrationTest {

    @Autowired
    private DirectBrowsingProvider testInstance;

    @Autowired
    private ScraperConfigurationService scraperConfigurationService;

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
                .expectNoEvent(scraperConfigurationService.getDelay(failed.getProvider()))
                .expectNoEvent(scraperConfigurationService.getDelay(failed.getProvider()))
                .verifyErrorMatches(ScraperTestData.responses().failed()::equals);
    }
}