package com.zephyr.location.data;

import com.zephyr.location.domain.Country;
import com.zephyr.test.Countries;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class CountryEntities {

    public Country ukraine() {
        Country result = new Country();
        result.setIso(Countries.UA_ISO);
        result.setName(Countries.UA_NAME);
        result.setLocaleGoogle(Countries.UA_LOCALE_GOOGLE);
        result.setLocaleYandex(Countries.UA_LOCALE_YANDEX);

        return result;
    }

    public Country canada() {
        Country result = new Country();
        result.setIso(Countries.CA_ISO);
        result.setName(Countries.CA_NAME);
        result.setLocaleGoogle(Countries.CA_LOCALE_GOOGLE);

        return result;
    }
}
