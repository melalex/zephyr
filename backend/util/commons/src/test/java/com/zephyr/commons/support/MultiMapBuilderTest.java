package com.zephyr.commons.support;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MultiMapBuilderTest {

    private static final String FIRST_KEY = "key1";
    private static final String FIRST_VALUE = "value1";

    @Test
    public void shouldPut() {
        assertTrue(MultiMapBuilder.create().put(FIRST_KEY, FIRST_VALUE).build().containsKey(FIRST_KEY));
    }

    @Test
    public void shouldPutIfTrue() {
        assertTrue(MultiMapBuilder.create().putIfTrue(FIRST_KEY, FIRST_VALUE, true).build().containsKey(FIRST_KEY));
    }

    @Test
    public void shouldNotPutIfFalse() {
        assertFalse(MultiMapBuilder.create().putIfTrue(FIRST_KEY, FIRST_VALUE, false).build().containsKey(FIRST_KEY));
    }

    @Test
    public void shouldPutIfNotNull() {
        assertTrue(MultiMapBuilder.create().putIfNotNull(FIRST_KEY, FIRST_VALUE).build().containsKey(FIRST_KEY));
    }

    @Test
    public void shouldNotPutIfNull() {
        assertFalse(MultiMapBuilder.create().putIfNotNull(FIRST_KEY, null).build().containsKey(FIRST_KEY));
    }
}