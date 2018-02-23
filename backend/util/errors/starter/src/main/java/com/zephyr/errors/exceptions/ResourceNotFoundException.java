package com.zephyr.errors.exceptions;

public class ResourceNotFoundException extends ParameterizedException {
    private static final long serialVersionUID = 3161593982223874979L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
