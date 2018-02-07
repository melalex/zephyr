package com.zephyr.errors.dsl;

import com.zephyr.errors.domain.*;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.utils.ErrorUtil;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public final class SimpleExceptionSpec<T extends ParameterizedException> {
    private int status = 400;
    private final T exception;
    private final SubjectSpec<Subject> subjects = Problems.subject();

    SimpleExceptionSpec(T exception) {
        this.exception = exception;
    }

    public SimpleExceptionSpec<T> status(int code) {
        this.status = code;
        return this;
    }

    public SimpleExceptionSpec<T> status(HttpStatus status) {
        this.status = status.value();
        return this;
    }

    public SimpleExceptionSpec<T> path(Path path) {
        subjects.path(path);
        return this;
    }

    public SimpleExceptionSpec<T> actual(Actual actual) {
        subjects.actual(actual);
        return this;
    }

    public SimpleExceptionSpec<T> reason(Reason reason) {
        subjects.reason(reason);
        return this;
    }

    public SimpleExceptionSpec<T> payload(Iterable<Object> payload) {
        subjects.payload(payload);
        return this;
    }

    public SimpleExceptionSpec<T> payload(Object payload) {
        subjects.payload(payload);
        return this;
    }

    public SimpleExceptionSpec<T> payload(Object first, Object... other) {
        subjects.payload(first, other);
        return this;
    }

    public T populate() {
        exception.setCode(ErrorUtil.errorCode(exception.getClass()));
        exception.setStatus(status);
        exception.setData(ErrorData.of(subjects.completeSubject()));

        return exception;
    }

    public Supplier<T> populatingFunction() {
        return this::populate;
    }

    public void populateAndThrow() {
        throw populate();
    }
}
