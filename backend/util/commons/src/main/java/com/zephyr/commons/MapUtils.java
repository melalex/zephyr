package com.zephyr.commons;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class MapUtils {

    public <K, V> V getOrThrow(Map<K, V> map, K key) {
        return Optional.ofNullable(map.get(key))
                .orElseThrow(() -> new IllegalArgumentException(errorMessage(key)));
    }

    public <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    private <K> String errorMessage(K key) {
        return String.format("Map doesn't contains key '%s'", key);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder<K, V> {
        private Map<K, V> prototype;

        public Builder<K, V> put(K key, V value) {
            prototype.put(key, value);
            return this;
        }

        public Builder<K, V> putIfTrue(K key, V value, boolean condition) {
            if (condition) {
                prototype.put(key, value);
            }
            return this;
        }

        public Builder<K, V> putIfNotNull(K key, V value) {
            if (Objects.nonNull(value)) {
                prototype.put(key, value);
            }
            return this;
        }

        public Builder<K, V> putIfTrueAndNotNull(K key, V value, boolean condition) {
            if (Objects.nonNull(value)) {
                return putIfTrue(key, value, condition);
            }
            return this;
        }

        public Map<K, V> build() {
            return ImmutableMap.copyOf(prototype);
        }
    }
}