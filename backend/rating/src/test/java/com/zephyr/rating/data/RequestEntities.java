package com.zephyr.rating.data;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.rating.domain.Request;
import com.zephyr.test.Results;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class RequestEntities {

    private QueryEntities queries;

    public Request bing() {
        return simple(SearchEngine.BING.name());
    }

    public Request google() {
        return simple(SearchEngine.GOOGLE.name());
    }

    public Request yahoo() {
        return simple(SearchEngine.YAHOO.name());
    }

    public Request simple(String provider) {
        Request result = new Request();
        result.setProvider(provider);
        result.setQuery(queries.simple());
        result.setTimestamp(Results.SIMPLE_TIMESTAMP);

        return result;
    }
}
