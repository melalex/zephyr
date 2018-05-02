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

    public static final int CALGARY_ID = 1001801;
    public static final String CALGARY_NAME = "Calgary";
    public static final String CALGARY_CANONICAL_NAME = "Calgary,Alberta,Canada";
    public static final int CALGARY_PARENT = 20113;
    public static final PlaceType CALGARY_TYPE = PlaceType.CITY;
    public static final String CALGARY_UULE = "w+CAIQICIWQ2FsZ2FyeSxBbGJlcnRhLENhbmFkYQ";

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

    public PlaceDto calgary() {
        PlaceDto result = new PlaceDto();
        result.setId(CALGARY_ID);
        result.setName(CALGARY_NAME);
        result.setCanonicalName(CALGARY_CANONICAL_NAME);
        result.setParent(CALGARY_PARENT);
        result.setType(CALGARY_TYPE);
        result.setUule(CALGARY_UULE);
        result.setCountry(countries.canada());

        return result;
    }
}
