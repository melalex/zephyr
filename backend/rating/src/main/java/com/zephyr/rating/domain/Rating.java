package com.zephyr.rating.domain;

import com.zephyr.data.enums.SearchEngine;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class Rating implements Cloneable {

    @Id
    private String id;

    private int position;

    @NonNull
    private String url;

    @NonNull
    private Query query;

    private LocalDateTime timestamp;
    private SearchEngine provider;

    @SneakyThrows
    public Rating withLinkAndPosition(String url, int position) {
        Rating clone = clone();
        clone.setUrl(url);
        clone.setPosition(position);

        return clone;
    }

    @Override
    protected Rating clone() throws CloneNotSupportedException {
        return (Rating) super.clone();
    }
}