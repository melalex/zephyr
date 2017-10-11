package com.zephyr.errors.utils;

import com.google.common.base.CaseFormat;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.SubjectError;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorUtil {
    public static final String ERROR_CODE_PREFIX = "error";
    public static final String ERROR_CODE_SEPARATOR = ".";

    public String toCamel(@NonNull final Enum<?> value) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value.name());
    }

    public SubjectError firstError(@NonNull final ErrorData errorData) {
        if (errorData.getSubjectErrors().isEmpty()) {
            throw new IllegalArgumentException("ErrorData must contains at least one SubjectError");
        }

        return errorData.getSubjectErrors().get(0);
    }

    public String errorCode(@NonNull final Class<?> clazz) {
        return clazz.getName();
    }
}