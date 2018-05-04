package com.zephyr.test;

import com.zephyr.data.internal.dto.QueryDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class Queries {

    private Places places;
    private UserAgents userAgents;

    public QueryDto simple() {
        QueryDto result = new QueryDto();
        result.setQuery(Criteria.SIMPLE_QUERY);
        result.setLanguageIso(Criteria.SIMPLE_LANGUAGE_ISO);
        result.setPlace(places.kiev());
        result.setUserAgent(userAgents.windowsFirefox());

        return result;
    }

    public QueryDto fromNewCriteria() {
        QueryDto result = new QueryDto();
        result.setQuery(Criteria.NEW_CRITERIA_QUERY);
        result.setLanguageIso(Criteria.NEW_CRITERIA_LANGUAGE_ISO);
        result.setPlace(places.calgary());
        result.setUserAgent(userAgents.macSafari());

        return result;
    }

    public QueryDto withoutAgent() {
        QueryDto result = fromNewCriteria();
        result.setUserAgent(null);

        return result;
    }
}
