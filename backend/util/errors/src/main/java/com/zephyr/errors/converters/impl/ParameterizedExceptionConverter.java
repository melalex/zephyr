package com.zephyr.errors.converters.impl;

import com.zephyr.errors.converters.ProblemConverter;
import com.zephyr.errors.converters.internal.MessageSourceHolder;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.Subject;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.messages.Problem;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ParameterizedExceptionConverter implements ProblemConverter<ParameterizedException> {

    @Setter(onMethod = @__(@Autowired))
    private MessageSourceHolder messageSource;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Override
    public Problem convert(ParameterizedException exception, Locale locale) {
        return Problem.builder()
                .timestamp(LocalDateTime.now(clock))
                .type(exception.getCode())
                .status(exception.getStatus())
                .detail(exception.getLocalizedMessage())
                .errors(toErrors(exception.getData(), locale))
                .build();
    }

    private List<Problem.NestedError> toErrors(ErrorData data, Locale locale) {
        return data.getSubjects().stream()
                .map(toNestedError(locale))
                .collect(Collectors.toList());
    }

    private Function<Subject, Problem.NestedError> toNestedError(Locale locale) {
        return e -> Problem.NestedError.builder()
                .field(e.getPath().getPathCode())
                .rejected(e.getActual().getValue())
                .message(messageSource.getMessage(e.getCode(), e.getPayloadAsArray(), locale))
                .build();
    }
}