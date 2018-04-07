package com.zephyr.validation.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.zephyr.validation.FieldMatch;
import lombok.Value;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

@RunWith(MockitoJUnitRunner.class)
public class FieldMatchValidatorTest {

    private static final String FIRST_PROPERTY = "first";
    private static final String SECOND_PROPERTY = "second";

    private static final String VALID_VALUE = "valid";
    private static final String FIRST_VALUE = "first";
    private static final String SECOND_VALUE = "second";

    private static final ConstraintValidatorContext CONSTRAINT_VALIDATOR_CONTEXT = null;
    private final FieldMatchValidator testInstance = new FieldMatchValidator();
    @Mock
    private FieldMatch fieldMatch;

    @Before
    public void setUp() {
        when(fieldMatch.first()).thenReturn(FIRST_PROPERTY);
        when(fieldMatch.second()).thenReturn(SECOND_PROPERTY);

        testInstance.initialize(fieldMatch);
    }

    @Test
    public void shouldReturnTrue() {
        TestObject testObject = new TestObject(VALID_VALUE, VALID_VALUE);

        assertTrue(testInstance.isValid(testObject, CONSTRAINT_VALIDATOR_CONTEXT));
    }

    @Test
    public void shouldReturnFalse() {
        TestObject testObject = new TestObject(FIRST_VALUE, SECOND_VALUE);

        assertFalse(testInstance.isValid(testObject, CONSTRAINT_VALIDATOR_CONTEXT));
    }

    @Value
    private static class TestObject {

        private String first;
        private String second;
    }
}