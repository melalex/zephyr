package com.zephyr.errors.dsl;

import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.utils.ErrorUtil;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

public final class ExceptionSpec<T extends ParameterizedException> {
    private final T exception;

    private int status = 400;
    private ErrorData data;

    ExceptionSpec(T exception) {
        this.exception = exception;
    }

    public ExceptionSpec<T> status(int code) {
        this.status = code;
        return this;
    }

    public ExceptionSpec<T> status(HttpStatus status) {
        this.status = status.value();
        return this;
    }

    public ErrorDataSpec<ExceptionSpec<T>> data() {
        return new ErrorDataSpec<>(this::onDataBuilt);
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

    private ExceptionSpec<T> onDataBuilt(ErrorData data) {
        this.data = data;
        return this;
    }
}