package com.zephyr.commons;

import lombok.Value;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class ListUtils {

    public <T> T random(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    public <T> SafeVarArg<T> toSafeVarArg(List<T> list, Class<T> clazz) {
        var first = list.stream()
                .findFirst()
                .orElse(null);

        var size = list.size();

        List<T> subList = Collections.emptyList();

        if (size > 2) {
            subList = list.subList(1, size);
        }

        @SuppressWarnings("unchecked")
        var rest = (T[]) Array.newInstance(clazz, subList.size());
        rest = subList.toArray(rest);

        return SafeVarArg.of(first, rest);
    }

    @Value(staticConstructor = "of")
    public static class SafeVarArg<T> {

        private T first;
        private T[] rest;
    }
}
