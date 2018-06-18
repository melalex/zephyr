package com.zephyr.errors.converters.impl;

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

@Component
public class UnhandledExceptionConverter implements ProblemConverter<Throwable> {

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "error.server.manager";

    @Setter(onMethod = @__(@Autowired))
    private MessageSourceHolder messageSource;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Override
    public Problem convert(Throwable exception, Locale locale) {
        return Problem.builder()
                .timestamp(LocalDateTime.now(clock))
                .type(ErrorUtil.errorCode(exception.getClass()))
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(exception.getLocalizedMessage())
                .errors(getErrors(locale))
                .build();
    }

    private List<Problem.NestedError> getErrors(Locale locale) {
        var error = Problem.NestedError.builder()
                .message(messageSource.getMessage(INTERNAL_SERVER_ERROR_MESSAGE, locale))
                .build();

        return List.of(error);
    }
}
