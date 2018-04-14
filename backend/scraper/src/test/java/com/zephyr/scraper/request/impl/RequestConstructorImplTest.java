package com.zephyr.scraper.request.impl;

import static org.mockito.Mockito.when;

import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.provider.RequestProvider;
import com.zephyr.test.extensions.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import reactor.test.StepVerifier;

import java.util.List;

@Tag("request")
@ExtendWith(MockitoExtension.class)
class RequestConstructorImplTest {

    @Mock
    private RequestProvider bing;

    @Mock
    private RequestProvider google;

    @Mock
    private RequestProvider yahoo;

    private RequestConstructorImpl testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new RequestConstructorImpl(List.of(bing, google, yahoo));
    }

    @Test
    void shouldConstruct() {
        Query query = ScraperTestData.queries().simple();

        EngineRequest bingFirstPage = ScraperTestData.requests().bing().firstPage();
        EngineRequest bingSecondPage = ScraperTestData.requests().bing().secondPage();
        EngineRequest googleFirstPage = ScraperTestData.requests().google().firstPage();
        EngineRequest googleSecondPage = ScraperTestData.requests().google().secondPage();
        EngineRequest yahooFirstPage = ScraperTestData.requests().yahoo().firstPage();
        EngineRequest yahooSecondPage = ScraperTestData.requests().yahoo().secondPage();

        when(bing.provide(query)).thenReturn(List.of(bingFirstPage, bingSecondPage));
        when(google.provide(query)).thenReturn(List.of(googleFirstPage, googleSecondPage));
        when(yahoo.provide(query)).thenReturn(List.of(yahooFirstPage, yahooSecondPage));

        StepVerifier.create(testInstance.construct(query))
                .expectNext(bingFirstPage)
                .expectNext(bingSecondPage)
                .expectNext(googleFirstPage)
                .expectNext(googleSecondPage)
                .expectNext(yahooFirstPage)
                .expectNext(yahooSecondPage)
                .verifyComplete();
    }
}