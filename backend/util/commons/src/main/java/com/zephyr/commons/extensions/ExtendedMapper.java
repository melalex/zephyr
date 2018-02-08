package com.zephyr.commons.extensions;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@AllArgsConstructor
public class ExtendedMapper {

    @Delegate
    private final ModelMapper modelMapper;

    public <T, R> Function<T, R> mapperFor(Class<R> clazz) {
        return v -> Objects.nonNull(v) ? modelMapper.map(v, clazz) : null;
    }

    public <T, R> Mono<R> mapAsync(T object, Class<R> clazz) {
        return Mono.just(modelMapper.map(object, clazz));
    }
}
