package com.zephyr.errors.domain;

import com.zephyr.errors.utils.ErrorUtil;
import lombok.Value;

import java.io.Serializable;

@Value
public class Actual implements Serializable {

    private static final long serialVersionUID = -7410033108304395134L;

    private String value;

    public static Actual isA(String value) {
        return new Actual(value);
    }

    public static Actual isA(long value) {
        return isA(String.valueOf(value));
    }

    public static Actual isA(double value) {
        return isA(String.valueOf(value));
    }

    public static Actual isA(Object value) {
        return isA(String.valueOf(value));
    }

    public static Actual isA(Enum<?> value) {
        return isA(ErrorUtil.identifier(value));
    }
}
