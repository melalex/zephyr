package com.zephyr.errors.domain;

import com.zephyr.errors.utils.ErrorUtil;
import lombok.Value;

import java.io.Serializable;

@Value
public class Field implements Serializable {
    private static final long serialVersionUID = 4205492112692729453L;

    private String value;

    public static Field isA(final String value) {
        return new Field(value);
    }

    public static Field isA(final long value) {
        return isA(String.valueOf(value));
    }

    public static Field isA(final double value) {
        return isA(String.valueOf(value));
    }

    public static Field isA(final Object value) {
        return isA(String.valueOf(value));
    }

    public static Field isA(final Enum<?> value) {
        return isA(ErrorUtil.toCamel(value));
    }
}
