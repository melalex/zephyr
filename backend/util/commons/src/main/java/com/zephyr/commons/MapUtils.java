package com.zephyr.commons;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Streams;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class MapUtils {

    public <K, V> V getOrThrow(Map<K, V> map, K key) {
        return Optional.ofNullable(map.get(key))
                .orElseThrow(() -> new IllegalArgumentException(errorMessage(key)));
    }

    public MultiValueMapBuilder multiValueMapBuilder() {
        return new MultiValueMapBuilder();
    }

    private <K> String errorMessage(K key) {
        return String.format("Map doesn't contains key '%s'", key);
    }

    @SafeVarargs
    public <K, V> Map<K, V> merge(Map<K, V> ...maps) {
        return Stream.of(maps)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MultiValueMapBuilder {
        private final Map<String, List<String>> prototype = new HashMap<>();

        public MultiValueMapBuilder put(String key, String value) {
            prototype.put(key, ImmutableList.of(value));
            return this;
        }

        public MultiValueMapBuilder putAll(Map<String, List<String>> map) {
            prototype.putAll(map);
            return this;
        }

        public MultiValueMapBuilder put(String key, int value) {
            return put(key, String.valueOf(value));
        }

        public MultiValueMapBuilder putIfTrue(String key, String value, boolean condition) {
            if (condition) {
                prototype.put(key, ImmutableList.of(value));
            }
            return this;
        }

        public MultiValueMapBuilder putIfTrue(String key, int value, boolean condition) {
            return putIfTrue(key, String.valueOf(value), condition);
        }

        public MultiValueMapBuilder putIfNotNull(String key, String value) {
            if (Objects.nonNull(value)) {
                prototype.put(key, ImmutableList.of(value));
            }
            return this;
        }

        public Map<String, List<String>> build() {
            return ImmutableMap.copyOf(prototype);
        }
    }
}
