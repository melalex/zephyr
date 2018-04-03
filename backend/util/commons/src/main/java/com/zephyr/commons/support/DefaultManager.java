package com.zephyr.commons.support;

import com.zephyr.commons.MapUtils;
import com.zephyr.commons.interfaces.Manager;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor(staticName = "of")
public final class DefaultManager<K, V> implements Manager<K, V> {

    private Map<K, V> map;

    @Override
    public V manage(K key) {
        return MapUtils.getOrThrow(map, key);
    }
}
