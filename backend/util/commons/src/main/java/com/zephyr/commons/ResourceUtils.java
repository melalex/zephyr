package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@UtilityClass
public class ResourceUtils {

    public String toString(String name) {
        return new Scanner(ResourceUtils.class.getResourceAsStream(name), StandardCharsets.UTF_8.name())
                .useDelimiter("\\A").next();
    }
}
