package com.zephyr.errors.utils;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.Subject;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class ErrorUtil {

    public static final String ERROR_CODE_PREFIX = "error";
    public static final Joiner ERROR_CODE_JOINER = Joiner.on(".");

    private static final String AT_LEAST_ONE_ERROR_MESSAGE = "ErrorData must contains at least one Subject";

    private static final String WRAPPER = "'";
    private static final String EMPTY = WRAPPER + " " + WRAPPER;

    public String identifier(@NonNull Enum<?> value) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value.name());
    }

    public String identifier(@NonNull Class<?> clazz) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, clazz.getName());
    }

    public String errorCode(@NonNull Class<?> clazz) {
        return clazz.getSimpleName();
    }

    public String wrapValue(Object value) {
        return Optional.ofNullable(value)
                .map(p -> WRAPPER + p + WRAPPER)
                .orElse(EMPTY);
    }

    public Subject firstError(@NonNull ErrorData errorData) {
        Preconditions.checkState(errorData.hasErrors(), AT_LEAST_ONE_ERROR_MESSAGE);

        return errorData.getSubjects().get(0);
    }
}