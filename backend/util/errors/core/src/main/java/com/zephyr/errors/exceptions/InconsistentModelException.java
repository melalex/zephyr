package com.zephyr.errors.exceptions;

public class InconsistentModelException extends ParameterizedException {

    private static final long serialVersionUID = -1165663331800746600L;

    public InconsistentModelException() {
        super();
    }

    public InconsistentModelException(String message) {
        super(message);
    }

    public InconsistentModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public InconsistentModelException(Throwable cause) {
        super(cause);
    }
}
