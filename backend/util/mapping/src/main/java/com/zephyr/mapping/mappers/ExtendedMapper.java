package com.zephyr.mapping.mappers;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.modelmapper.ModelMapper;

import java.util.function.Function;

@AllArgsConstructor
public class ExtendedMapper {

    // TODO: Uncomment when lombok become compatible with jdk 9
    //@Delegate
    private final ModelMapper modelMapper;

    public <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public <T, R> Function<T, R> mapTo(Class<R> clazz) {
        return v -> modelMapper.map(v, clazz);
    }
}
