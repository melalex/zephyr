package com.zephyr.rating.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class Rating {

    public static final String COLLECTION_NAME = "Rating";
    public static final String URL_FIELD = "url";
    public static final String QUERY_FIELD = "request.query";
    public static final String TIMESTAMP_FIELD = "request.timestamp";
    public static final String QUERY_STRING_FIELD = "request.query.query";
    public static final String PROVIDER_FIELD = "request.provider";

    @Id
    private String id;

    @NonNull
    private Request request;

    @NonNull
    private String url;

    private Integer position;

    public Rating(Request request, String url, Integer position) {
        this.request = request;
        this.url = url;
        this.position = position;
    }
}