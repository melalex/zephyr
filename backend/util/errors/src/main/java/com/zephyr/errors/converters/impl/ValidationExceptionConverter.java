package com.zephyr.errors.converters.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Streams;
import com.zephyr.errors.converters.ProblemConverter;
import com.zephyr.errors.converters.internal.MessageSourceHolder;
import com.zephyr.errors.messages.Problem;
import com.zephyr.errors.utils.ErrorUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ValidationExceptionConverter implements ProblemConverter<ConstraintViolationException> {
    private static final int UNPROCESSABLE_ENTITY = 422;

    @Setter(onMethod = @__(@Autowired))
    private MessageSourceHolder messageSource;

    @Override
    public Problem convert(final ConstraintViolationException exception, final Locale locale) {
        return Problem.builder()
                .type(ErrorUtil.errorCode(exception.getClass()))
                .detail(exception.getLocalizedMessage())
                .status(UNPROCESSABLE_ENTITY)
                .errors(toErrors(exception, locale))
                .build();
    }

    private List<Problem.NestedError> toErrors(final ConstraintViolationException exception, final Locale locale) {
        return exception.getConstraintViolations().stream()
                .map(toNestedError(locale))
                .collect(Collectors.toList());
    }

    private Function<ConstraintViolation, Problem.NestedError> toNestedError(final Locale locale) {
        return v -> Problem.NestedError.builder()
                .field(getField(v))
                .rejected(String.valueOf(v.getInvalidValue()))
                .message(messageSource.getMessage(getCode(v), locale))
                .build();
    }

    private String getCode(final ConstraintViolation violation) {
        List<String> path = Streams.stream(violation.getPropertyPath())
                .map(Path.Node::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return Joiner.on(ErrorUtil.ERROR_CODE_SEPARATOR)
                .join(violation.getRootBean(), path);
    }

    private String getField(final ConstraintViolation violation) {
        return Streams.stream(violation.getPropertyPath())
                .filter(n -> ElementKind.PROPERTY.equals(n.getKind()))
                .map(Path.Node::getName)
                .filter(Objects::nonNull)
                .reduce((f, s) -> s)
                .orElse(null);
    }
}