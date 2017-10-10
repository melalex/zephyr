package com.zephyr.errors.converters.impl;

import com.zephyr.errors.converters.ProblemConverter;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.messages.Problem;
import org.springframework.stereotype.Component;

@Component
public class ParameterizedExceptionConverter implements ProblemConverter<ParameterizedException> {

    @Override
    public Problem convert(final ParameterizedException exception) {
        return null;
    }
}
