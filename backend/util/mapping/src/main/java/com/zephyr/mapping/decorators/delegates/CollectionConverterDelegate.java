package com.zephyr.mapping.decorators.delegates;

import com.zephyr.mapping.support.impl.ConverterSupport;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Collection;
import java.util.stream.Stream;

@AllArgsConstructor(staticName = "of")
public class CollectionConverterDelegate<S, D> {
    private ConverterSupport<S, D> converter;

    public Stream<D> convert(@NonNull Collection<S> source) {
        return source.stream().map(converter::convert);
    }

    public Stream<S> reverseConvert(@NonNull Collection<D> source) {
        return source.stream().map(converter.getReverse()::convert);
    }
}
