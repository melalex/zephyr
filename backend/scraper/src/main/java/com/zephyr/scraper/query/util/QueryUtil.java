package com.zephyr.scraper.query.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.net.URLEncoder;

@UtilityClass
public class QueryUtil {
    private static final String ENCODING = "UTF-8";

    @SneakyThrows
    public String encode(String string) {
        return URLEncoder.encode(string, ENCODING);
    }
}
