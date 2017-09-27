package com.zephyr.mapping.decorators;

import com.zephyr.mapping.decorators.delegates.CollectionConverterDelegate;
import com.zephyr.mapping.support.impl.ConverterSupport;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ToSetConverter<S, D> extends ConverterSupport<Collection<S>, Collection<D>> {
    private CollectionConverterDelegate<S, D> delegate;

    public static <S, D> ToSetConverter<S, D> of(ConverterSupport<S, D> converter) {
        return new ToSetConverter<>(CollectionConverterDelegate.of(converter));
    }

    @Override
    public Set<D> convert(@NonNull Collection<S> source) {
        return delegate.convert(source).collect(toSet());
    }

    @Override
    protected Set<S> reverseConvert(@NonNull Collection<D> source) {
        return delegate.reverseConvert(source).collect(toSet());
    }
}
