package com.zephyr.commons.templates;

import com.zephyr.commons.interfaces.Provider;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public abstract class AbstractProvider<K> implements Provider<K> {
    private K key;

    @Override
    public Set<K> supports() {
        return Set.of(key);
    }
}
