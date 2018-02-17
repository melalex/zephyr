package com.zephyr.commons.interfaces;

@FunctionalInterface
public interface Handler<T> {

    void handle(T target);
}
