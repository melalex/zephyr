package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class MapUtils {

    private static final String GET_ERROR_MESSAGE = "Map doesn't contains key '%s'";

    public <K, V> V getOrThrow(Map<K, V> map, K key) {
        return Optional.ofNullable(map.get(key))
                .orElseThrow(() -> new IllegalArgumentException(String.format(GET_ERROR_MESSAGE, key)));
    }

    public <K, U> Collector<Map.Entry<K, U>, ?, Map<K, U>> toMap() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n) -> n);
    }

    public <K, V> Function<Map<K, V>, Stream<Map.Entry<K, V>>> unwrap() {
        return m -> m.entrySet().stream();
    }
}
