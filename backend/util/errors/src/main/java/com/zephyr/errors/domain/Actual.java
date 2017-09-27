package com.zephyr.errors.domain;

import com.zephyr.errors.ErrorUtil;
import lombok.Value;

import java.io.Serializable;

@Value
@SuppressWarnings("unused, WeakerAccess")
public class Actual implements Serializable {
    private static final long serialVersionUID = 3699816084045842912L;

    private String value;

    public static Actual isA(final String value) {
        return new Actual(value);
    }

    public static Actual isA(final long value) {
        return isA(String.valueOf(value));
    }

    public static Actual isA(final double value) {
        return isA(String.valueOf(value));
    }

    public static Actual isA(final Object value) {
        return isA(String.valueOf(value));
    }

    public static Actual isA(final Enum<?> value) {
        return isA(ErrorUtil.toCamel(value));
    }
}
