package com.zephyr.errors.builders;

import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.utils.ErrorUtil;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressWarnings("unused, WeakerAccess")
public final class ExceptionPopulator<T extends ParameterizedException> {
    private final T exception;

    private int status = 500;
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

    public T build() {
        checkNotNull(data);

        exception.setCode(ErrorUtil.errorCode(exception.getClass()));
        exception.setStatus(status);
        exception.setData(data);

        return exception;
    }

    public T buildAndThrow() {
        throw build();
    }

    ExceptionPopulator<T> onDataBuilt(ErrorData data) {
        this.data = data;
        return this;
    }
}
