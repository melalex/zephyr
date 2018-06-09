package com.zephyr.commons;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.net.URL;

@UtilityClass
public class ResourceUtils {

    @SneakyThrows
    public String toString(String name) {
        URL url = Resources.getResource(name);
        return Resources.toString(url, Charsets.UTF_8);
    }
}
