package com.zephyr.errors.converters.impl;

import com.zephyr.errors.converters.ProblemConverter;
import com.zephyr.errors.converters.internal.MessageSourceHolder;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.SubjectError;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.messages.Problem;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ParameterizedExceptionConverter implements ProblemConverter<ParameterizedException> {

    @Setter(onMethod = @__(@Autowired))
    private MessageSourceHolder messageSource;

    @Override
    public Problem convert(final ParameterizedException exception, final Locale locale) {
        return Problem.builder()
                .type(exception.getCode())
                .status(exception.getStatus())
                .detail(exception.getLocalizedMessage())
                .errors(toErrors(exception.getData(), locale))
                .build();
    }

    private List<Problem.NestedError> toErrors(final ErrorData data, final Locale locale) {
        return data.getSubjectErrors().stream()
                .map(toNestedError(locale))
                .collect(Collectors.toList());
    }

    private Function<SubjectError, Problem.NestedError> toNestedError(final Locale locale) {
        return e -> Problem.NestedError.builder()
                .field(e.getFiled().getValue())
                .rejected(e.getActual().getValue())
                .message(messageSource.getMessage(e.getCode(), e.getPayloadAsArray(), locale))
                .build();
    }
}