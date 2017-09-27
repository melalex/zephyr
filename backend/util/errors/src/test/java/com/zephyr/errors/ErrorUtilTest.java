package com.zephyr.errors;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ErrorUtilTest {
    private static final String EXPECTED_VALUE = "testValue";
    private static final String EXPECTED_CODE = "errorUtilTest";

    private enum TestEnum {
        TEST_VALUE
    }

    @Test
    public void shouldGetValue() {
        String actual = ErrorUtil.toCamel(TestEnum.TEST_VALUE);

        assertEquals(EXPECTED_VALUE, actual);
    }

    @Test
    public void shouldReturnErrorCode() {
        assertEquals(EXPECTED_CODE, ErrorUtil.errorCode(getClass()));
    }
}