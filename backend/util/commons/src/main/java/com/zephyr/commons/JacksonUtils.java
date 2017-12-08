package com.zephyr.commons;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import lombok.experimental.UtilityClass;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class JacksonUtils {

    public <T> List<MappingJacksonValue> filter(Collection<T> target, Iterable<String> fields) {
        return target.stream()
                .map(t -> filter(t, fields))
                .collect(Collectors.toList());
    }

    public <T> MappingJacksonValue filter(T target, Iterable<String> fields) {
        MappingJacksonValue result = new MappingJacksonValue(target);
        result.setFilters(filterProvider(fields));

        return result;
    }

    private static FilterProvider filterProvider(Iterable<String> fields) {
        return null;
    }
}
