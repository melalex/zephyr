package com.zephyr.errors.utils;

import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.SubjectError;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class ErrorUtil {
    public static final String ERROR_CODE_PREFIX = "error";
    public static final String ERROR_CODE_SEPARATOR = ".";

    private static final String AT_LEAST_ONE_ERROR_MESSAGE = "ErrorData must contains at least one SubjectError";

    private static final String WRAPPER = "'";
    private static final String EMPTY = WRAPPER + " " + WRAPPER;

    public String toCamel(@NonNull final Enum<?> value) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value.name());
    }

    public String toCamel(@NonNull final Class<?> clazz) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, clazz.getName());
    }

    public String errorCode(@NonNull final Class<?> clazz) {
        return clazz.getName();
    }

    public String wrapValue(Object value) {
        return Optional.ofNullable(value)
                .map(p -> WRAPPER + p + WRAPPER)
                .orElse(EMPTY);
    }

    public SubjectError firstError(@NonNull final ErrorData errorData) {
        Preconditions.checkState(errorData.hasErrors(), AT_LEAST_ONE_ERROR_MESSAGE);

        return errorData.getSubjectErrors().get(0);
    }
}