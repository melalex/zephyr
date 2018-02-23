package com.zephyr.errors.converters;

import com.zephyr.errors.messages.Problem;

import java.util.Locale;

@FunctionalInterface
public interface ProblemConverter<T extends Throwable> {

    Problem convert(T exception, Locale locale);
}
