package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class MapUtils {
    private static final String GET_ERROR_MESSAGE = "Map doesn't contains key '%s'";

    public <K, V> V getOrThrow(Map<K, V> map, K key) {
        return Optional.ofNullable(map.get(key))
                .orElseThrow(() -> new IllegalArgumentException(String.format(GET_ERROR_MESSAGE, key)));
    }

    @SafeVarargs
    public <K, V> Map<K, V> merge(Map<K, V>... maps) {
        return Stream.of(maps)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
