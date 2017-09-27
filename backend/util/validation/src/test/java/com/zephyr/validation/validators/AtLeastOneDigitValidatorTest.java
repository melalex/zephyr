package com.zephyr.validation.validators;

import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AtLeastOneDigitValidatorTest {
    private static final String VALID_STRING = "Aa#1";
    private static final String INVALID_STRING = "aaaa";

    private static final ConstraintValidatorContext CONSTRAINT_VALIDATOR_CONTEXT = null;

    private final AtLeastOneDigitValidator testInstance = new AtLeastOneDigitValidator();

    @Test
    public void shouldReturnTrue() {
        assertTrue(testInstance.isValid(VALID_STRING, CONSTRAINT_VALIDATOR_CONTEXT));
    }

    @Test
    public void shouldReturnFalse() {
        assertFalse(testInstance.isValid(INVALID_STRING, CONSTRAINT_VALIDATOR_CONTEXT));
    }
}