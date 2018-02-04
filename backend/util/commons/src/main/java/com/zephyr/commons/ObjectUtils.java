package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class ObjectUtils {

    public boolean equalsOrNull(Object first, Object second) {
        return Optional.ofNullable(first)
                .map(f -> f.equals(second))
                .orElse(true);
    }
}
