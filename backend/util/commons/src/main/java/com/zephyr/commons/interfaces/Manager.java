package com.zephyr.commons.interfaces;

public interface Manager<K, V> {

    V manage(K key);
}
