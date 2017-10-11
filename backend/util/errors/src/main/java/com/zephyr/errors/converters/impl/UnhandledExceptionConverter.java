package com.zephyr.errors.converters.impl;

import com.zephyr.errors.converters.ProblemConverter;
import com.zephyr.errors.converters.internal.MessageSourceHolder;
import com.zephyr.errors.messages.Problem;
import com.zephyr.errors.utils.ErrorUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class UnhandledExceptionConverter implements ProblemConverter<Throwable> {
    private static final int INTERNAL_SERVER_ERROR_STATUS = 500;
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "error.server.internal";

    @Setter(onMethod = @__(@Autowired))
    private MessageSourceHolder messageSource;

    @Override
    public Problem convert(final Throwable exception, final Locale locale) {
        return Problem.builder()
                .type(ErrorUtil.errorCode(exception.getClass()))
                .status(INTERNAL_SERVER_ERROR_STATUS)
                .detail(exception.getLocalizedMessage())
                .errors(getErrors(locale))
                .build();
    }

    private List<Problem.NestedError> getErrors(final Locale locale) {
        return List.of(
                Problem.NestedError.builder()
                        .message(messageSource.getMessage(INTERNAL_SERVER_ERROR_MESSAGE, locale))
                        .build()
        );
    }
}
