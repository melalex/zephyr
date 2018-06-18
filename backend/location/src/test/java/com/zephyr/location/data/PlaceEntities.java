package com.zephyr.location.data;

import com.zephyr.location.domain.Place;
import com.zephyr.test.Places;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class PlaceEntities {

    private CountryEntities countries;

    public Place kiev() {
        Place result = new Place();
        result.setName(Places.KIEV_NAME);
        result.setCanonicalName(Places.KIEV_CANONICAL_NAME);
        result.setParent(new Place());
        result.setType(Place.PlaceType.valueOf(Places.KIEV_TYPE.name()));
        result.setCountry(countries.ukraine());

        return result;
    }

    public Place calgary() {
        Place result = new Place();
        result.setName(Places.CALGARY_NAME);
        result.setCanonicalName(Places.CALGARY_CANONICAL_NAME);
        result.setParent(new Place());
        result.setType(Place.PlaceType.valueOf(Places.CALGARY_TYPE.name()));
        result.setCountry(countries.canada());

        return result;
    }
}
