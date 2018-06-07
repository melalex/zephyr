package com.zephyr.data.util;

import com.zephyr.data.protocol.enums.SearchEngine;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ConversionUtils {

    public Set<String> toString(Collection<SearchEngine> engines) {
        return engines.stream()
                .map(Enum::toString)
                .collect(Collectors.toSet());
    }
}
