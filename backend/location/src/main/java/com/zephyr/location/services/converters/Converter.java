package com.zephyr.location.services.converters;

@FunctionalInterface
public interface Converter<S, D> {

    D convert(S source);
}
