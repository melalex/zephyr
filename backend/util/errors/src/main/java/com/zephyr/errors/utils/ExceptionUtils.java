package com.zephyr.errors.utils;

import com.zephyr.errors.domain.Actual;
import com.zephyr.errors.domain.Path;
import com.zephyr.errors.domain.Reasons;
import com.zephyr.errors.domain.Subject;
import com.zephyr.errors.dsl.Problems;
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

    public void assertErrors(@NonNull ParameterizedException exception, @NonNull Collection<Subject> errors) {
        if (!errors.isEmpty()) {
            // @formatter:off
            Problems.exception(exception)
                    .data()
                        .subjectErrors(errors)
                        .completeData()
                    .populateAndThrow();
            // @formatter:on
        }
    }

    public <T> Mono<T> notFound(Class<?> clazz, Object id) {
        return notFound(clazz.getName(), id);
    }

    public <T> Mono<T> notFound(String name, Object id) {
        return Mono.error(newNotFoundError(name, id).get());
    }

    public Supplier<ResourceNotFoundException> newNotFoundError(Class<?> clazz, Object id) {
        return newNotFoundError(ErrorUtil.identifier(clazz), id);
    }

    public Supplier<ResourceNotFoundException> newNotFoundError(String name, Object id) {
        // @formatter:off
        return Problems.exception(new ResourceNotFoundException(String.format(NOT_FOUND_ERROR_MESSAGE, name, id)))
                .status(HttpStatus.NOT_FOUND.value())
                .data()
                    .subjectError()
                        .path(Path.of(name))
                        .reason(Reasons.NOT_FOUND)
                        .actual(Actual.isA(id))
                        .payload(id)
                        .completeSubject()
                    .completeData()
                .populatingFunction();
        // @formatter:on
    }

    public <T> Mono<T> noCurrentUserAsync() {
        return Mono.error(noCurrentUser());
    }

    public CurrentUserNotSetException noCurrentUser() {
        // @formatter:off
        return Problems.exception(new CurrentUserNotSetException(NO_CURRENT_USER_MESSAGE))
                .status(HttpStatus.UNAUTHORIZED.value())
                .data()
                    .subjectError()
                        .path(Path.of(NO_CURRENT_USER_ROOT))
                        .reason(Reasons.NOT_SET)
                        .completeSubject()
                    .completeData()
                .populate();
        // @formatter:on
    }
}
