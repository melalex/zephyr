package com.zephyr.errors.dsl;

import com.zephyr.errors.domain.Actual;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.Path;
import com.zephyr.errors.domain.Reason;
import com.zephyr.errors.domain.Subject;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.utils.ErrorUtil;

import java.util.function.Supplier;

public final class SimpleExceptionSpec<T extends ParameterizedException> {

    private final T exception;
    private final SubjectSpec<Subject> subjects = Problems.subject();

    private int status = 400;

    SimpleExceptionSpec(T exception) {
        this.exception = exception;
    }

    public SimpleExceptionSpec<T> status(int status) {
        this.status = status;
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
        exception.setStatus(status);
        exception.setCode(ErrorUtil.errorCode(exception.getClass()));
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
