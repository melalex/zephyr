package com.zephyr.errors.domain;

import com.zephyr.errors.utils.ErrorUtil;
import lombok.Value;

import java.io.Serializable;

@Value
public class Reason implements Serializable {
    private static final long serialVersionUID = -6657199748489928416L;

    private String value;

    public static Reason isA(String value) {
        return new Reason(value);
    }

    public static Reason isA(long value) {
        return isA(String.valueOf(value));
    }

    public static Reason isA(double value) {
        return isA(String.valueOf(value));
    }

    public static Reason isA(Object value) {
        return isA(String.valueOf(value));
    }

    public static Reason isA(Enum<?> value) {
        return isA(ErrorUtil.identifier(value));
    }
}
