package com.zephyr.commons.interfaces;

public interface Manager<K, P extends Provider<K>> {

    P manage(K selector);
}
