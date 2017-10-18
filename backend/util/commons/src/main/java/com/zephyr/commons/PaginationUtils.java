package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@UtilityClass
public class PaginationUtils {

    public Stream<Integer> pagesStream(int count, int perPage) {
        return IntStream.range(0, MathUtils.roundUp(count, perPage)).boxed();
    }

    public int startOf(int page, int perPage) {
        return page * perPage;
    }
}
