package com.zephyr.rating.support;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.zephyr.rating.data.RatingTestData;
import org.junit.Test;

public class RequestMatcherTest {

    private RequestMatcher testInstance = new RequestMatcher();

    @Test
    public void shouldMatch() {
        var targetCriteria = RatingTestData.requestCriteria().simple();
        var targetRequest = RatingTestData.requests().google();

        assertTrue(testInstance.matches(targetCriteria, targetRequest));
    }

    @Test
    public void shouldMatchWhenAgentIsNull() {
        var targetCriteria = RatingTestData.requestCriteria().simple();
        var targetRequest = RatingTestData.requests().google();

        targetCriteria.getQueryCriteria().setUserAgent(null);

        assertTrue(testInstance.matches(targetCriteria, targetRequest));
    }

    @Test
    public void shouldReturnFalseWhenProviderNotMatch() {
        var targetCriteria = RatingTestData.requestCriteria().simple();
        var targetRequest = RatingTestData.requests().google();

        targetCriteria.setEngine(null);

        assertFalse(testInstance.matches(targetCriteria, targetRequest));
    }
}