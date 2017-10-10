package com.zephyr.auth.exceptions;

import com.zephyr.errors.utils.ErrorUtil;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.exceptions.ParameterizedException;

public class UserAlreadyExistsException extends ParameterizedException {
    private static final long serialVersionUID = -272023117009352457L;
    private static final String ERROR_CODE = ErrorUtil.errorCode(UserAlreadyExistsException.class);

    public UserAlreadyExistsException(ErrorData errorData) {
        super(ERROR_CODE, errorData);
    }

    public UserAlreadyExistsException(ErrorData errorData, String message) {
        super(ERROR_CODE, errorData, message);
    }

    public UserAlreadyExistsException(ErrorData errorData, String message, Throwable cause) {
        super(ERROR_CODE, errorData, message, cause);
    }

    public UserAlreadyExistsException(ErrorData errorData, Throwable cause) {
        super(ERROR_CODE, errorData, cause);
    }
}
