package com.zephyr.errors.dsl;

import com.google.common.base.Preconditions;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.utils.ErrorUtil;

import java.util.function.Supplier;

public final class ExceptionSpec<T extends ParameterizedException> {

    private final T exception;

    private int status = 400;
    private ErrorData data;

    ExceptionSpec(T exception) {
        this.exception = exception;
    }

    public ExceptionSpec<T> status(int status) {
        this.status = status;
        return this;
    }

    public ErrorDataSpec<ExceptionSpec<T>> data() {
        return new ErrorDataSpec<>(this::onDataBuilt);
    }

    public T populate() {
        Preconditions.checkNotNull(data);

        exception.setStatus(status);
        exception.setCode(ErrorUtil.errorCode(exception.getClass()));
        exception.setData(data);

        return exception;
    }

    public Supplier<T> populatingFunction() {
        return this::populate;
    }

    public void populateAndThrow() {
        throw populate();
    }

    private ExceptionSpec<T> onDataBuilt(ErrorData data) {
        this.data = data;
        return this;
    }
}