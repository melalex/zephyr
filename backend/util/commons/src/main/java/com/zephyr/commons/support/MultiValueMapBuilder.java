package com.zephyr.commons.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiValueMapBuilder {

    private final Map<String, List<String>> prototype = new HashMap<>();

    public static MultiValueMapBuilder create() {
        return new MultiValueMapBuilder();
    }

    public MultiValueMapBuilder put(String key, String value) {
        prototype.put(key, List.of(value));
        return this;
    }

    public MultiValueMapBuilder put(String key, int value) {
        return put(key, String.valueOf(value));
    }

    public MultiValueMapBuilder putIfTrue(String key, String value, boolean condition) {
        if (condition) {
            prototype.put(key, List.of(value));
        }
        return this;
    }

    public MultiValueMapBuilder putIfTrue(String key, int value, boolean condition) {
        return putIfTrue(key, String.valueOf(value), condition);
    }

    public MultiValueMapBuilder putIfNotNull(String key, String value) {
        if (Objects.nonNull(value)) {
            prototype.put(key, List.of(value));
        }
        return this;
    }

    public Map<String, List<String>> build() {
        return new HashMap<>(prototype);
    }
}
