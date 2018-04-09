package com.zephyr.test;

import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.data.protocol.enums.PlaceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class Places {

    public static final int KIEV_ID = 1012852;
    public static final String KIEV_NAME = "Kiev";
    public static final String KIEV_CANONICAL_NAME = "Kiev,Kyiv city,Ukraine";
    public static final int KIEV_PARENT = 21118;
    public static final PlaceType KIEV_TYPE = PlaceType.CITY;
    public static final String KIEV_UULE = "w+CAIQICIWS2lldixLeWl2IGNpdHksVWtyYWluZQ";

    private Countries countries;

    public PlaceDto kiev() {
        PlaceDto result = new PlaceDto();
        result.setId(KIEV_ID);
        result.setName(KIEV_NAME);
        result.setCanonicalName(KIEV_CANONICAL_NAME);
        result.setParent(KIEV_PARENT);
        result.setType(KIEV_TYPE);
        result.setUule(KIEV_UULE);
        result.setCountry(countries.ukraine());

        return result;
    }
}
