package com.zephyr.validation.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.zephyr.validation.DateRange;
import lombok.Value;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import javax.validation.ConstraintValidatorContext;

@RunWith(MockitoJUnitRunner.class)
public class DateRangeValidatorTest {

    private static final String FROM_PROPERTY = "from";
    private static final String TO_PROPERTY = "to";

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
        var now = LocalDate.now();
        var testObject = new TestObject(now, now.plusDays(1));

        assertTrue(testInstance.isValid(testObject, CONSTRAINT_VALIDATOR_CONTEXT));
    }

    @Test
    public void shouldReturnFalse() {
        var now = LocalDate.now();
        var testObject = new TestObject(now, now.minusDays(1));

        assertFalse(testInstance.isValid(testObject, CONSTRAINT_VALIDATOR_CONTEXT));
    }

    @Value
    public static class TestObject {

        private LocalDate from;
        private LocalDate to;
    }
}