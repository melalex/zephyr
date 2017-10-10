package com.zephyr.errors.converters;

import com.zephyr.errors.messages.Problem;

@FunctionalInterface
public interface ProblemConverter<T extends Throwable> {

    Problem convert(T exception);
}
