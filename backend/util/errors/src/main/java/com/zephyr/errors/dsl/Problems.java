package com.zephyr.errors.dsl;

import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.SubjectError;
import com.zephyr.errors.exceptions.ParameterizedException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Problems {

    public ErrorDataSpec<ErrorData> data() {
        return new ErrorDataSpec<>(d -> d);
    }

    public SubjectSpec<SubjectError> subject() {
        return new SubjectSpec<>(s -> s);
    }

    public <T extends ParameterizedException> ExceptionSpec<T> exception(T exception) {
        return new ExceptionSpec<>(exception);
    }
}
