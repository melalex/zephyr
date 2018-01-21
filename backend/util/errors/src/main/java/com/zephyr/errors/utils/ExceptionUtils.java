package com.zephyr.errors.utils;

import com.google.common.base.CaseFormat;
import com.zephyr.errors.builders.ExceptionPopulator;
import com.zephyr.errors.exceptions.ResourceNotFoundException;
import com.zephyr.errors.exceptions.CurrentUserNotSetException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@UtilityClass
public class ExceptionUtils {
    private static final String NOT_FOUND_ERROR_ROOT = "notFound";
    private static final String NOT_FOUND_ERROR_MESSAGE = "Resource '%s' with id '%s' not found";

    private static final String NO_CURRENT_USER_ROOT = "user";
    private static final String NO_CURRENT_USER_ERROR = "notSet";
    private static final String NO_CURRENT_USER_MESSAGE = "Current User not set";

    public <T> Mono<T> notFound(final Class<?> clazz, final Object id) {
        return notFound(clazz.getName(), id);
    }

    public <T> Mono<T> notFound(final String name, final Object id) {
        return Mono.error(newNotFoundError(name, id).get());
    }

    public Supplier<ResourceNotFoundException> newNotFoundError(final Class<?> clazz, final Object id) {
        return newNotFoundError(clazz.getName(), id);
    }

    public Supplier<ResourceNotFoundException> newNotFoundError(final String name, final Object id) {
        // @formatter:off
        return ExceptionPopulator.of(new ResourceNotFoundException(String.format(NOT_FOUND_ERROR_MESSAGE, name, id)))
                .status(HttpStatus.NOT_FOUND.value())
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
                .populatingFunction();
        // @formatter:on
    }

    public <T> Mono<T> noCurrentUserAsync() {
        return Mono.error(noCurrentUser());
    }

    public CurrentUserNotSetException noCurrentUser() {
        // @formatter:off
        return ExceptionPopulator.of(new CurrentUserNotSetException(NO_CURRENT_USER_MESSAGE))
                .status(HttpStatus.UNAUTHORIZED.value())
                .data()
                    .subjectError()
                        .path()
                            .root(NO_CURRENT_USER_ROOT)
                            .with(NO_CURRENT_USER_ERROR)
                            .add()
                        .add()
                    .complete()
                .populate();
        // @formatter:on
    }

}
