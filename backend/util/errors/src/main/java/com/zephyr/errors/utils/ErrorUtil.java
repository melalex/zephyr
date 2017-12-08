package com.zephyr.errors.utils;

import com.google.common.base.CaseFormat;
import com.zephyr.errors.builders.ExceptionPopulator;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.SubjectError;
import com.zephyr.errors.exceptions.ResourceNotFoundException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@UtilityClass
public class ErrorUtil {
    private static final int NOT_FOUND_STATUS_CODE = 404;

    private static final String NOT_FOUND_ERROR_MESSAGE = "Resource '%s' with id '%s' not found";
    private static final String AT_LEAST_ONE_ERROR_MESSAGE = "ErrorData must contains at least one SubjectError";
    private static final String NOT_FOUND_ERROR_ROOT = "notFound";

    public static final String ERROR_CODE_PREFIX = "error";
    public static final String ERROR_CODE_SEPARATOR = ".";

    public String toCamel(@NonNull final Enum<?> value) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value.name());
    }

    public SubjectError firstError(@NonNull final ErrorData errorData) {
        if (errorData.getSubjectErrors().isEmpty()) {
            throw new IllegalArgumentException(AT_LEAST_ONE_ERROR_MESSAGE);
        }

        return errorData.getSubjectErrors().get(0);
    }

    public String errorCode(@NonNull final Class<?> clazz) {
        return clazz.getName();
    }

    public <T> Mono<T> notFound(final Class<?> clazz, final Object id) {
        return notFound(clazz.getName(), id);
    }

    public <T> Mono<T> notFound(final String name, final Object id) {
        return Mono.error(newNotFoundError(name, id).get());
    }

    public Supplier<ResourceNotFoundException> newNotFoundError(final Class<?> clazz, final Object id)  {
        return newNotFoundError(clazz.getName(), id);
    }

    public Supplier<ResourceNotFoundException> newNotFoundError(final String name, final Object id) {
        // @formatter:off
        return () -> ExceptionPopulator.of(new ResourceNotFoundException(String.format(NOT_FOUND_ERROR_MESSAGE, name, id)))
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
    }
}