package com.zephyr.rating.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class Rating {

    @Id
    private String id;

    @DBRef
    @NonNull
    private Request request;

    @NonNull
    private int position;

    @NonNull
    private String url;
}