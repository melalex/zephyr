package com.zephyr.commons.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiMapBuilder {

    private final Map<String, List<String>> prototype = new HashMap<>();

    public static MultiMapBuilder create() {
        return new MultiMapBuilder();
    }

    public MultiMapBuilder put(String key, String value) {
        var mapValue = prototype.getOrDefault(key, new LinkedList<>());
        mapValue.add(value);
        prototype.put(key, mapValue);
        return this;
    }

    public MultiMapBuilder put(String key, int value) {
        return put(key, String.valueOf(value));
    }

    public MultiMapBuilder putIfTrue(String key, String value, boolean condition) {
        if (condition) {
            put(key, value);
        }
        return this;
    }

    public MultiMapBuilder putIfTrue(String key, int value, boolean condition) {
        return putIfTrue(key, String.valueOf(value), condition);
    }

    public MultiMapBuilder putIfNotNull(String key, String value) {
        if (Objects.nonNull(value)) {
            put(key, value);
        }
        return this;
    }

    public Map<String, List<String>> build() {
        return new HashMap<>(prototype);
    }
}
