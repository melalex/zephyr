package com.zephyr.errors.converters.impl;

import com.google.common.collect.Streams;
import com.zephyr.errors.converters.ProblemConverter;
import com.zephyr.errors.converters.internal.MessageSourceHolder;
import com.zephyr.errors.messages.Problem;
import com.zephyr.errors.utils.ErrorUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;

@Component
public class ValidationExceptionConverter implements ProblemConverter<ConstraintViolationException> {

    @Setter(onMethod = @__(@Autowired))
    private MessageSourceHolder messageSource;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Override
    public Problem convert(ConstraintViolationException exception, Locale locale) {
        return Problem.builder()
                .timestamp(LocalDateTime.now(clock))
                .type(ErrorUtil.errorCode(exception.getClass()))
                .detail(exception.getLocalizedMessage())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .errors(toErrors(exception, locale))
                .build();
    }

    private List<Problem.NestedError> toErrors(ConstraintViolationException exception, Locale locale) {
        return exception.getConstraintViolations().stream()
                .map(toNestedError(locale))
                .collect(Collectors.toList());
    }

    private Function<ConstraintViolation, Problem.NestedError> toNestedError(Locale locale) {
        return v -> Problem.NestedError.builder()
                .field(getField(v))
                .rejected(String.valueOf(v.getInvalidValue()))
                .message(messageSource.getMessage(getCode(v), locale))
                .build();
    }

    private String getCode(ConstraintViolation violation) {
        var path = Streams.stream(violation.getPropertyPath())
                .map(Path.Node::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return ErrorUtil.ERROR_CODE_JOINER
                .join(violation.getRootBean(), path);
    }

    private String getField(ConstraintViolation violation) {
        return Streams.stream(violation.getPropertyPath())
                .filter(n -> ElementKind.PROPERTY.equals(n.getKind()))
                .map(Path.Node::getName)
                .filter(Objects::nonNull)
                .reduce((f, s) -> s)
                .orElse(null);
    }
}