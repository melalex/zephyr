package com.zephyr.commons;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@UtilityClass
public class StreamUtils {

    public <T> Stream<Pair<Integer, T>> zipWithIndexes(List<T> list, int offset) {
        return IntStream.range(offset, list.size() + offset)
                .boxed()
                .map(i -> Pair.of(i, list.get(i)));
    }

    public <T> Stream<Pair<Integer, T>> zipWithIndexes(List<T> list) {
        return zipWithIndexes(list, 0);
    }
}
