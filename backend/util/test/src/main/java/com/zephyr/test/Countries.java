package com.zephyr.test;

import com.zephyr.data.protocol.dto.CountryDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class Countries {

    public static final String UA_ISO = "UA";
    public static final String UA_NAME = "Ukraine";
    public static final String UA_LOCALE_GOOGLE = "https://www.google.com.ua";
    public static final String UA_LOCALE_YANDEX = "https://yandex.ua";

    public CountryDto ukraine() {
        CountryDto result = new CountryDto();
        result.setIso(UA_ISO);
        result.setName(UA_NAME);
        result.setLocaleGoogle(UA_LOCALE_GOOGLE);
        result.setLocaleYandex(UA_LOCALE_YANDEX);

        return result;
    }
}
