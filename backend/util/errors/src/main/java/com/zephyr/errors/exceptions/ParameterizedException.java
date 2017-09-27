package com.zephyr.errors.exceptions;

import com.zephyr.errors.domain.ErrorData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@SuppressWarnings("unused")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParameterizedException extends RuntimeException {
    private static final long serialVersionUID = -4858804588507017853L;

    private String code;
    private ErrorData errorData;

    public ParameterizedException(final String code, final ErrorData errorData) {
        this.code = code;
        this.errorData = errorData;
    }

    public ParameterizedException(final String code, final ErrorData errorData, final String message) {
        super(message);
        this.code = code;
        this.errorData = errorData;
    }

    public ParameterizedException(final String code, final ErrorData errorData, final String message, final Throwable cause) {
        super(message, cause);
        this.code = code;
        this.errorData = errorData;
    }

    public ParameterizedException(final String code, final ErrorData errorData, final Throwable cause) {
        super(cause);
        this.code = code;
        this.errorData = errorData;
    }
}
