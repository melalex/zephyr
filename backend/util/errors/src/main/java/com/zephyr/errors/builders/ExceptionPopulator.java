package com.zephyr.errors.builders;

import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.utils.ErrorUtil;

import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

public final class ExceptionPopulator<T extends ParameterizedException> {
    private final T exception;

    private int status = 400;
    private ErrorData data;

    public static <T extends ParameterizedException> ExceptionPopulator<T> of(T exception) {
        return new ExceptionPopulator<>(exception);
    }

    private ExceptionPopulator(T exception) {
        this.exception = exception;
    }

    public ExceptionPopulator<T> status(final int code) {
        this.status = code;
        return this;
    }

    public ErrorDataBuilder<T> data() {
        return new ErrorDataBuilder<>(this);
    }

    public T populate() {
        checkNotNull(data);

        exception.setCode(ErrorUtil.errorCode(exception.getClass()));
        exception.setStatus(status);
        exception.setData(data);

        return exception;
    }

    public Supplier<T> populatingFunction() {
        return this::populate;
    }

    public void populateAndThrow() {
        throw populate();
    }

    ExceptionPopulator<T> onDataBuilt(ErrorData data) {
        this.data = data;
        return this;
    }
}