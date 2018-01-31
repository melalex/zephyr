package com.zephyr.commons.interfaces;

@FunctionalInterface
public interface Transformer<S, R> {

    R transform(S source);
}
