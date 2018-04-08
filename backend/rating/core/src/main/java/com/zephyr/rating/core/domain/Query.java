package com.zephyr.rating.core.domain;

import lombok.Data;

@Data
public class Query {

    private String query;
    private String languageIso;
    private Place place;
    private UserAgent userAgent;
}
