package com.zephyr.scraper.request;

import com.zephyr.scraper.ScraperTestConfiguration;
import com.zephyr.scraper.data.ScraperTestData;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@SpringBootTest
@ActiveProfiles("requestTest")
@ExtendWith(SpringExtension.class)
@Import(ScraperTestConfiguration.class)
@Tags({@Tag("request"), @Tag("integration")})
class RequestConstructorIntegrationTest {

    @Autowired
    private RequestConstructor testInstance;

    @Test
    void shouldConstruct() {
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
                .expectNext(ScraperTestData.requests().yandex().secondPage());
    }
}