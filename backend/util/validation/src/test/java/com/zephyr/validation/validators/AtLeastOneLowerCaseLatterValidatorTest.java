package com.zephyr.validation.validators;

import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.*;

public class AtLeastOneLowerCaseLatterValidatorTest {
    private static final String VALID_STRING = "Aa#1";
    private static final String INVALID_STRING = "AAAA";

    private static final ConstraintValidatorContext CONSTRAINT_VALIDATOR_CONTEXT = null;

    private final AtLeastOneLowerCaseLatterValidator testInstance = new AtLeastOneLowerCaseLatterValidator();

    @Test
    public void shouldReturnTrue() {
        assertTrue(testInstance.isValid(VALID_STRING, CONSTRAINT_VALIDATOR_CONTEXT));
    }

    @Test
    public void shouldReturnFalse() {
        assertFalse(testInstance.isValid(INVALID_STRING, CONSTRAINT_VALIDATOR_CONTEXT));
    }
}