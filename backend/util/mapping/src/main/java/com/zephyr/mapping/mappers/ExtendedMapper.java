package com.zephyr.mapping.mappers;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.modelmapper.ModelMapper;

import java.util.Objects;
import java.util.function.Function;

@AllArgsConstructor
public class ExtendedMapper {

    @Delegate
    private final ModelMapper modelMapper;

    public <T, R> Function<T, R> mapperFor(Class<R> clazz) {
        return v -> Objects.nonNull(v) ? modelMapper.map(v, clazz) : null;
    }
}
