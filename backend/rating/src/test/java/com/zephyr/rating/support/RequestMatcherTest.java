package com.zephyr.rating.support;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.zephyr.rating.data.RatingTestData;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.domain.RequestCriteria;
import org.junit.Test;

public class RequestMatcherTest {

    private RequestMatcher testInstance = new RequestMatcher();

    @Test
    public void shouldMatch() {
        RequestCriteria targetCriteria = RatingTestData.requestCriteria().simple();
        Request targetRequest = RatingTestData.requests().google();

        assertTrue(testInstance.matches(targetCriteria, targetRequest));
    }

    @Test
    public void shouldMatchWhenAgentIsNull() {
        RequestCriteria targetCriteria = RatingTestData.requestCriteria().simple();
        Request targetRequest = RatingTestData.requests().google();

        targetCriteria.getQueryCriteria().setUserAgent(null);

        assertTrue(testInstance.matches(targetCriteria, targetRequest));
    }

    @Test
    public void shouldReturnFalseWhenProviderNotMatch() {
        RequestCriteria targetCriteria = RatingTestData.requestCriteria().simple();
        Request targetRequest = RatingTestData.requests().google();

        targetCriteria.setEngine(null);

        assertFalse(testInstance.matches(targetCriteria, targetRequest));
    }
}