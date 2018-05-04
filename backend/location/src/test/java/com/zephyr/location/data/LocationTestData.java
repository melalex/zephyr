package com.zephyr.location.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocationTestData {

    private static final LocationTestData INSTANCE = new LocationTestData();

    private CountryEntities countryEntities = new CountryEntities();
    private PlaceEntities placeEntities = new PlaceEntities(countryEntities);
    private LanguageEntities languageEntities = new LanguageEntities();

    public static CountryEntities countries() {
        return INSTANCE.countryEntities;
    }

    public static PlaceEntities places() {
        return INSTANCE.placeEntities;
    }

    public static LanguageEntities languages() {
        return INSTANCE.languageEntities;
    }
}
