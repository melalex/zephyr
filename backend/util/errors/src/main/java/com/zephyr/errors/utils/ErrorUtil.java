package com.zephyr.errors.utils;

import com.google.common.base.CaseFormat;
import com.zephyr.errors.builders.ExceptionPopulator;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.SubjectError;
import com.zephyr.errors.exceptions.ResourceNotFoundException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

@UtilityClass
public class ErrorUtil {
    private static final int NOT_FOUND_STATUS_CODE = 404;
    private static final String NOT_FOUND_ERROR_ROOT = "notFound";

    public static final String ERROR_CODE_PREFIX = "error";
    public static final String ERROR_CODE_SEPARATOR = ".";

    public String toCamel(@NonNull final Enum<?> value) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value.name());
    }

    public SubjectError firstError(@NonNull final ErrorData errorData) {
        if (errorData.getSubjectErrors().isEmpty()) {
            throw new IllegalArgumentException("ErrorData must contains at least one SubjectError");
        }

        return errorData.getSubjectErrors().get(0);
    }

    public String errorCode(@NonNull final Class<?> clazz) {
        return clazz.getName();
    }

    public <T> Mono<T> notFound(final String name, final Object id) {
        final String message = String.format("Resource '%s' with id '%s' not found", name, id);

        // @formatter:off
        final ResourceNotFoundException exception = ExceptionPopulator.of(new ResourceNotFoundException(message))
                .status(NOT_FOUND_STATUS_CODE)
                .data()
                    .subjectError()
                        .path()
                            .root(NOT_FOUND_ERROR_ROOT)
                            .with(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, name))
                            .add()
                        .actual(id)
                        .payload(id)
                        .add()
                    .complete()
                .populate();
        // @formatter:on

        return Mono.error(exception);
    }
}