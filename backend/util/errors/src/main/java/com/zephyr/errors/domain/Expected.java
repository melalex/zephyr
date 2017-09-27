package com.zephyr.errors.domain;

import com.zephyr.errors.ErrorUtil;
import lombok.Value;

import java.io.Serializable;

@Value
@SuppressWarnings("unused, WeakerAccess")
public class Expected implements Serializable {
    private static final long serialVersionUID = 4205492112692729453L;

    private String value;

    public static Expected isA(final String value) {
        return new Expected(value);
    }

    public static Expected isA(final long value) {
        return isA(String.valueOf(value));
    }

    public static Expected isA(final double value) {
        return isA(String.valueOf(value));
    }

    public static Expected isA(final Object value) {
        return isA(String.valueOf(value));
    }

    public static Expected isA(final Enum<?> value) {
        return isA(ErrorUtil.toCamel(value));
    }
}
