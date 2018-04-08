package com.zephyr.test;

import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.data.protocol.enums.PlaceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class Places {

    private Countries countries;

    public PlaceDto kiev() {
        PlaceDto result = new PlaceDto();
        result.setId(1012852);
        result.setName("Kiev");
        result.setCanonicalName("Kiev,Kyiv city,Ukraine");
        result.setParent(21118);
        result.setType(PlaceType.CITY);
        result.setUule("w+CAIQICIWS2lldixLeWl2IGNpdHksVWtyYWluZQ");
        result.setCountry(countries.ukraine());

        return result;
    }
}
