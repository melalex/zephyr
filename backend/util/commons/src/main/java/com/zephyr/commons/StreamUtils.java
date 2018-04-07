package com.zephyr.commons;

import com.zephyr.commons.support.Indexed;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@UtilityClass
public class StreamUtils {

    public <T> Stream<Indexed<T>> zipWithIndexes(List<T> list, int offset) {
        return IntStream.range(offset, list.size() + offset)
                .mapToObj(i -> Indexed.of(i, list.get(i)));
    }

    public <T> Stream<Indexed<T>> zipWithIndexes(List<T> list) {
        return zipWithIndexes(list, 0);
    }

    public IntStream counter(int to) {
        return IntStream.range(0, to);
    }

    @SafeVarargs
    public <T> Stream<T> concat(Stream<T>... streams) {
        return Stream.of(streams)
                .reduce(Stream::concat)
                .orElseGet(Stream::empty);
    }

    public Stream<String> range(char first, char last) {
        return IntStream.rangeClosed(first, last)
                .mapToObj(i -> (char) i)
                .map(Object::toString);
    }

    public <T> BinaryOperator<List<T>> mergeLists() {
        return (l1, l2) -> Stream.concat(l1.stream(), l2.stream())
                .collect(Collectors.toList());
    }
}
