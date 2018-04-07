package com.zephyr.validation.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.zephyr.validation.DateRange;
import lombok.Value;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

@RunWith(MockitoJUnitRunner.class)
public class DateRangeValidatorTest {

    private static final String FROM_PROPERTY = "from";
    private static final String TO_PROPERTY = "to";
    private static final long DURATION = 9999;

    private static final ConstraintValidatorContext CONSTRAINT_VALIDATOR_CONTEXT = null;
    private final DateRangeValidator testInstance = new DateRangeValidator();
    @Mock
    private DateRange dateRange;

    @Before
    public void setUp() {
        when(dateRange.from()).thenReturn(FROM_PROPERTY);
        when(dateRange.to()).thenReturn(TO_PROPERTY);

        testInstance.initialize(dateRange);
    }

    @Test
    public void shouldReturnTrue() {
        DateTime now = DateTime.now();
        TestObject testObject = new TestObject(now, now.plus(DURATION));

        assertTrue(testInstance.isValid(testObject, CONSTRAINT_VALIDATOR_CONTEXT));
    }

    @Test
    public void shouldReturnFalse() {
        DateTime now = DateTime.now();
        TestObject testObject = new TestObject(now, now.minus(DURATION));

        assertFalse(testInstance.isValid(testObject, CONSTRAINT_VALIDATOR_CONTEXT));
    }

    @Value
    private static class TestObject {

        private DateTime from;
        private DateTime to;
    }
}