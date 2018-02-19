package com.zephyr.commons.interfaces;

import java.util.Set;

public interface Provider<K> {

    Set<K> supports();
}
