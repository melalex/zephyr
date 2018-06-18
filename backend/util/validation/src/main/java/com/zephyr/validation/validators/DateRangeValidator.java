package com.zephyr.validation.validators;

import com.zephyr.validation.DateRange;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

    private String fromProperty;
    private String toProperty;

    @Override
    public void initialize(final DateRange constraintAnnotation) {
        fromProperty = constraintAnnotation.from();
        toProperty = constraintAnnotation.to();
    }

    @Override
    @SneakyThrows
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        final var fromValue = BeanUtils.getProperty(value, fromProperty);
        final var toValue = BeanUtils.getProperty(value, toProperty);

        if (StringUtils.isNoneEmpty(fromValue, toValue)) {
            final var from = LocalDate.parse(fromValue);
            final var to = LocalDate.parse(toValue);

            return from.isBefore(to);
        }

        return true;
    }
}
