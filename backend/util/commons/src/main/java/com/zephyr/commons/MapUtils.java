package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Optional;

@UtilityClass
public class MapUtils {

    public <K, V> V getOrThrow(Map<K, V> map, K key) {
        return Optional.ofNullable(map.get(key))
                .orElseThrow(() -> new IllegalArgumentException(errorMessage(key)));
    }

    private <K> String errorMessage(K key) {
        return String.format("Map doesn't contains key '%s'", key);
    }
}
