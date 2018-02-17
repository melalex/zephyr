package com.zephyr.commons.interfaces;

@FunctionalInterface
public interface EventPublisher<T> {

    void publish(T event);
}
