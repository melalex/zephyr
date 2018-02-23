package com.zephyr.errors.dsl;

@FunctionalInterface
public interface AssembleCallback<T, R> {

    T onAssemble(R result);
}
