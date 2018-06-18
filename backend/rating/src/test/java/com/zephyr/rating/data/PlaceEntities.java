package com.zephyr.rating.data;

import com.zephyr.rating.domain.Place;
import com.zephyr.test.Countries;
import com.zephyr.test.Places;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class PlaceEntities {

    public Place kiev() {
        var result = new Place();
        result.setName(Places.KIEV_NAME);
        result.setCountry(Countries.UA_ISO);

        return result;
    }

    public Place calgary() {
        var result = new Place();
        result.setName(Places.CALGARY_NAME);
        result.setCountry(Countries.CA_ISO);

        return result;
    }
}
