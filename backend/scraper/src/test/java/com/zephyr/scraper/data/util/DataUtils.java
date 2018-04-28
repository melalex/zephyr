package com.zephyr.scraper.data.util;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class DataUtils {

    public List<String> value(Object value) {
        return List.of(String.valueOf(value));
    }
}
