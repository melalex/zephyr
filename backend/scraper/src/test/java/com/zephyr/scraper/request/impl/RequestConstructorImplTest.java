package com.zephyr.scraper.request.impl;

import com.zephyr.scraper.request.provider.RequestProvider;
import com.zephyr.scraper.util.ScraperTestData;
import com.zephyr.test.extensions.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.test.StepVerifier;

@Tag("request")
@ExtendWith(MockitoExtension.class)
class RequestConstructorImplTest {

    @Mock
    private RequestProvider first;

    @Mock
    private RequestProvider second;

    @Mock
    private RequestProvider third;

    @InjectMocks
    private RequestConstructorImpl testInstance;

    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldConstruct() {
        StepVerifier.create(testInstance.construct(ScraperTestData.queries().simple()))
                .expectNext();
    }
}