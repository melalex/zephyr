package com.zephyr.scraper.domain;

import lombok.Value;
import org.springframework.http.MediaType;

@Value(staticConstructor = "of")
public class PageResponse {
    private MediaType mediaType;
    private Object content;
    private int number;

    public <T> T getContent(Class<T> clazz) {
        if (clazz.isInstance(content)) {
            return clazz.cast(content);
        } else {
            String message = String.format("content type is '%s' not '%s'", content.getClass(), clazz);
            throw new IllegalStateException(message);
        }
    }
}
