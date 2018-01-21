package com.zephyr.errors.exceptions;

public class CurrentUserNotSetException extends ParameterizedException {
    private static final long serialVersionUID = 7568027470502715910L;

    public CurrentUserNotSetException() {
        super();
    }

    public CurrentUserNotSetException(String message) {
        super(message);
    }

    public CurrentUserNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrentUserNotSetException(Throwable cause) {
        super(cause);
    }

}
