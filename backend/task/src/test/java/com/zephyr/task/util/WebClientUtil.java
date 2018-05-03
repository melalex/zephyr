package com.zephyr.task.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@UtilityClass
public class WebClientUtil {

    @SafeVarargs
    public <T> Function<UriBuilder, URI> from(T... fragments) {
        return u -> {
            for (T fragment : fragments) {
                u.fragment(Objects.toString(fragment));
            }
            return u.build();
        };
    }
}
