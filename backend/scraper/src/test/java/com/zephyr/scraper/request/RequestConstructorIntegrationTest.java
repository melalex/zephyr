package com.zephyr.scraper.request;

import com.zephyr.scraper.ScraperTestConfiguration;
import com.zephyr.scraper.data.ScraperTestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

@SpringBootTest
@ActiveProfiles("requestTest")
@RunWith(SpringRunner.class)
public class RequestConstructorIntegrationTest {

    @Autowired
    private RequestConstructor testInstance;

    @Test
    public void shouldConstruct() {
        StepVerifier.create(testInstance.construct(ScraperTestData.queries().simple()))
                .expectNext(ScraperTestData.requests().bing().firstPage())
                .expectNext(ScraperTestData.requests().bing().secondPage())
                .expectNext(ScraperTestData.requests().duckDuckGo().firstPage())
                .expectNext(ScraperTestData.requests().duckDuckGo().secondPage())
                .expectNext(ScraperTestData.requests().google().firstPage())
                .expectNext(ScraperTestData.requests().google().secondPage())
                .expectNext(ScraperTestData.requests().yahoo().firstPage())
                .expectNext(ScraperTestData.requests().yahoo().secondPage())
                .expectNext(ScraperTestData.requests().yandex().firstPage())
                .expectNext(ScraperTestData.requests().yandex().secondPage())
                .verifyComplete();
    }

    @TestConfiguration
    @Import(ScraperTestConfiguration.class)
    public static class Configuration {

    }
}