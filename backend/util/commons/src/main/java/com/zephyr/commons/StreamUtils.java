package com.zephyr.commons;

import lombok.Value;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@UtilityClass
public class StreamUtils {

    public <T> Stream<ZippedWithIndex<T>> zipWithIndexes(List<T> list, int offset) {
        return IntStream.range(offset, list.size() + offset)
                .boxed()
                .map(i -> ZippedWithIndex.of(i, list.get(i)));
    }

    public <T> Stream<ZippedWithIndex<T>> zipWithIndexes(List<T> list) {
        return zipWithIndexes(list, 0);
    }

    @Value(staticConstructor = "of")
    public static class ZippedWithIndex<T> {
        private int index;
        private T element;
    }
}
