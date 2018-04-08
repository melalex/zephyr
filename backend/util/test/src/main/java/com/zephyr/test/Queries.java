package com.zephyr.test;

import com.zephyr.data.internal.dto.QueryDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class Queries {

    public static final String SIMPLE_QUERY = "zephyr";
    public static final String SIMPLE_LANGUAGE_ISO = "en";

    private Places places;
    private UserAgents userAgents;

    public QueryDto simple() {
        QueryDto result = new QueryDto();
        result.setQuery(SIMPLE_QUERY);
        result.setLanguageIso(SIMPLE_LANGUAGE_ISO);
        result.setPlace(places.kiev());
        result.setUserAgent(userAgents.windowsFirefox());

        return result;
    }
}
