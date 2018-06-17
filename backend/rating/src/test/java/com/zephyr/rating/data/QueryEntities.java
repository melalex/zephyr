package com.zephyr.rating.data;

import com.zephyr.rating.domain.QueryCriteria;
import com.zephyr.test.Criteria;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class QueryEntities {

    private PlaceEntities places;
    private UserAgentEntities userAgents;

    public QueryCriteria simple() {
        QueryCriteria result = new QueryCriteria();
        result.setQuery(Criteria.SIMPLE_QUERY);
        result.setLanguageIso(Criteria.SIMPLE_LANGUAGE_ISO);
        result.setPlace(places.kiev());
        result.setUserAgent(userAgents.windowsFirefox());

        return result;
    }

    public QueryCriteria withoutAgent() {
        QueryCriteria result = simple();
        result.setUserAgent(null);

        return result;
    }
}
