package com.zephyr.errors.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ErrorUtilTest {

    private static final String EXPECTED_VALUE = "testValue";
    private static final String EXPECTED_CODE = "errorUtilTest";

    @Test
    public void shouldGetValue() {
        var actual = ErrorUtil.identifier(TestEnum.TEST_VALUE);

        assertEquals(EXPECTED_VALUE, actual);
    }

    @Test
    public void shouldReturnErrorCode() {
        assertEquals(EXPECTED_CODE, ErrorUtil.errorCode(getClass()));
    }

    private enum TestEnum {
        TEST_VALUE
    }
}