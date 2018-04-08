package com.zephyr.test;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonTestData {

    private static final CommonTestData INSTANCE = new CommonTestData();

    private Countries countries = new Countries();
    private UserAgents userAgents = new UserAgents();
    private Places places = new Places(countries);
    private Queries queries = new Queries(places, userAgents);

    public static Countries countries() {
        return INSTANCE.countries;
    }

    public static UserAgents userAgents() {
        return INSTANCE.userAgents;
    }

    public static Places places() {
        return INSTANCE.places;
    }

    public static Queries queries() {
        return INSTANCE.queries;
    }
}
