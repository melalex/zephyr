package com.zephyr.errors.dsl;

import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.Subject;
import com.zephyr.errors.exceptions.ParameterizedException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Problems {

    public ErrorDataSpec<ErrorData> data() {
        return new ErrorDataSpec<>(d -> d);
    }

    public SubjectSpec<Subject> subject() {
        return new SubjectSpec<>(s -> s);
    }

    public <T extends ParameterizedException> ExceptionSpec<T> exception(T exception) {
        return new ExceptionSpec<>(exception);
    }

    public <T extends ParameterizedException> SimpleExceptionSpec<T> simpleException(T exception) {
        return new SimpleExceptionSpec<>(exception);
    }

    public ExceptionSpec<ParameterizedException> exception(String message) {
        return exception(new ParameterizedException(message));
    }
}
