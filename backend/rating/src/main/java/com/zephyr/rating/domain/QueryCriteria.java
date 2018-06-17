package com.zephyr.rating.domain;

import lombok.Data;

@Data
public class QueryCriteria {

    private String query;
    private String languageIso;
    private Place place;
    private UserAgent userAgent;
}
