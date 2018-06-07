package com.zephyr.rating.support;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.zephyr.rating.data.RatingTestData;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.domain.RequestCriteria;
import org.junit.Test;

import java.util.Set;

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

        targetCriteria.getQuery().setUserAgent(null);

        assertTrue(testInstance.matches(targetCriteria, targetRequest));
    }

    @Test
    public void shouldReturnFalseWhenProviderNotMatch() {
        RequestCriteria targetCriteria = RatingTestData.requestCriteria().simple();
        Request targetRequest = RatingTestData.requests().google();

        targetCriteria.setEngines(Set.of());

        assertFalse(testInstance.matches(targetCriteria, targetRequest));
    }
}