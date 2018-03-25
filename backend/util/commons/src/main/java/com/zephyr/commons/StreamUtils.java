package com.zephyr.commons;

import com.zephyr.commons.support.Indexed;
import lombok.experimental.UtilityClass;

import java.util.List;
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
}
