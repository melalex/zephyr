package com.zephyr.rating.data;

import com.zephyr.test.CommonTestData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RatingTestData {

    private static final RatingTestData INSTANCE = new RatingTestData();

    private PlaceEntities places = new PlaceEntities();
    private UserAgentEntities userAgents = new UserAgentEntities();
    private QueryEntities queries = new QueryEntities(places, userAgents);
    private RequestEntities requests = new RequestEntities(queries);
    private RatingEntities ratings = new RatingEntities(CommonTestData.criteria());

    public static PlaceEntities places() {
        return INSTANCE.places;
    }

    public static UserAgentEntities userAgents() {
        return INSTANCE.userAgents;
    }

    public static QueryEntities queries() {
        return INSTANCE.queries;
    }

    public static RequestEntities requests() {
        return INSTANCE.requests;
    }

    public static RatingEntities ratings() {
        return INSTANCE.ratings;
    }
}
