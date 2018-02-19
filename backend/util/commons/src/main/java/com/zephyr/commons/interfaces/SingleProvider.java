package com.zephyr.commons.interfaces;

import java.util.Set;

public interface SingleProvider<K> extends Provider<K> {

    @Override
    default Set<K> supports() {
        return Set.of(supportSingle());
    }

    K supportSingle();
}
