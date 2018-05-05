package com.zephyr.rating.data;

import com.zephyr.rating.domain.Query;
import com.zephyr.test.Criteria;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class QueryEntities {

    private PlaceEntities places;
    private UserAgentEntities userAgents;

    public Query simple() {
        Query result = new Query ();
        result.setQuery(Criteria.SIMPLE_QUERY);
        result.setLanguageIso(Criteria.SIMPLE_LANGUAGE_ISO);
        result.setPlace(places.kiev());
        result.setUserAgent(userAgents.windowsFirefox());

        return result;
    }

    public Query withoutAgent() {
        Query result = simple();
        result.setUserAgent(null);

        return result;
    }
}
