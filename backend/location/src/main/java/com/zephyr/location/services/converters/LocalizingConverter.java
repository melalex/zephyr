package com.zephyr.location.services.converters;

import java.util.Locale;

@FunctionalInterface
public interface LocalizingConverter<S, R> {

    R convert(S source, Locale locale);
}
