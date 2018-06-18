package com.zephyr.scraper.request.impl;

import static org.mockito.Mockito.when;

import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.request.provider.RequestProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.test.StepVerifier;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RequestConstructorImplTest {

    @Mock
    private RequestProvider bing;

    @Mock
    private RequestProvider google;

    @Mock
    private RequestProvider yahoo;

    private RequestConstructorImpl testInstance;

    @Before
    public void setUp() {
        testInstance = new RequestConstructorImpl(List.of(bing, google, yahoo));
    }

    @Test
    public void shouldConstruct() {
        var query = ScraperTestData.queries().simple();

        var bingFirstPage = ScraperTestData.requests().bing().firstPage();
        var bingSecondPage = ScraperTestData.requests().bing().secondPage();
        var googleFirstPage = ScraperTestData.requests().google().firstPage();
        var googleSecondPage = ScraperTestData.requests().google().secondPage();
        var yahooFirstPage = ScraperTestData.requests().yahoo().firstPage();
        var yahooSecondPage = ScraperTestData.requests().yahoo().secondPage();

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