package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class ListUtils {

    public <T> T random(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}
