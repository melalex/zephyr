package com.zephyr.errors.utils;

import com.google.common.base.CaseFormat;
import com.zephyr.errors.domain.Actual;
import com.zephyr.errors.domain.Reason;
import com.zephyr.errors.dsl.ExceptionPopulator;
import com.zephyr.errors.domain.SubjectError;
import com.zephyr.errors.exceptions.CurrentUserNotSetException;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.exceptions.ResourceNotFoundException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.function.Supplier;

@UtilityClass
public class ExceptionUtils {
    private static final String NOT_FOUND_ERROR_MESSAGE = "Resource '%s' with id '%s' not found";

    private static final String NO_CURRENT_USER_ROOT = "user";
    private static final String NO_CURRENT_USER_MESSAGE = "Current User not set";

    public void assertErrors(@NonNull ParameterizedException exception, @NonNull Collection<SubjectError> errors) {
        if (!errors.isEmpty()) {
            // @formatter:off
            ExceptionPopulator.of(exception)
                    .data()
                        .subjectErrors(errors)
                        .complete()
                    .populateAndThrow();
            // @formatter:on
        }
    }

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
                            .root(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, name))
                            .with(Reason.NOT_FOUND)
                            .add()
                        .actual(Actual.isA(id))
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
                            .with(Reason.IS_NOT_SET)
                            .add()
                        .add()
                    .complete()
                .populate();
        // @formatter:on
    }

}
