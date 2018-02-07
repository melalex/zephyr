package com.zephyr.errors.exceptions;

import com.zephyr.errors.domain.ErrorData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ParameterizedException extends RuntimeException {
    private static final long serialVersionUID = -4858804588507017853L;

    private int status;
    private String code;
    private ErrorData data;

    public ParameterizedException() {
        super();
    }

    public ParameterizedException(String message) {
        super(message);
    }

    public ParameterizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterizedException(Throwable cause) {
        super(cause);
    }
}
