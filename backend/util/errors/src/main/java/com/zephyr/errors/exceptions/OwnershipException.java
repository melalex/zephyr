package com.zephyr.errors.exceptions;

public class OwnershipException extends ParameterizedException {

    private static final long serialVersionUID = -5909114048852137225L;

    public OwnershipException() {
    }

    public OwnershipException(String message) {
        super(message);
    }

    public OwnershipException(String message, Throwable cause) {
        super(message, cause);
    }

    public OwnershipException(Throwable cause) {
        super(cause);
    }
}
