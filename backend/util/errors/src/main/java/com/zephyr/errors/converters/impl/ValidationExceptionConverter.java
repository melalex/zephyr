package com.zephyr.errors.converters.impl;

import com.zephyr.errors.converters.ProblemConverter;
import com.zephyr.errors.messages.Problem;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;

@Component
public class ValidationExceptionConverter implements ProblemConverter<ConstraintViolationException> {

    @Override
    public Problem convert(final ConstraintViolationException exception) {
        return null;
    }
}
