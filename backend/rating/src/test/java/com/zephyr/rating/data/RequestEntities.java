package com.zephyr.rating.data;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.rating.domain.Request;
import com.zephyr.test.Results;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class RequestEntities {

    public static final String GOOGLE_ID = "8912aa3e-0c60-4c7c-b751-dde3333e74da";
    public static final String BING_ID = "f6c3bc0c-6e88-4c3c-83ef-a5663d7d3711";
    public static final String YAHOO_ID = "f3ce7548-9023-40ea-9931-b58c33e6043a";

    private QueryEntities queries;

    public Request bing() {
        return simple(GOOGLE_ID, SearchEngine.GOOGLE.name());
    }

    public Request google() {
        return simple(BING_ID, SearchEngine.BING.name());
    }

    public Request yahoo() {
        return simple(YAHOO_ID, SearchEngine.YAHOO.name());
    }

    public Request simple(String id, String provider) {
        Request result = new Request();
        result.setId(id);
        result.setProvider(provider);
        result.setQuery(queries.simple());
        result.setTimestamp(Results.SIMPLE_TIMESTAMP);

        return result;
    }
}
