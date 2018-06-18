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

    public static final String CA_ISO = "CA";
    public static final String CA_NAME = "Canada";
    public static final String CA_LOCALE_GOOGLE = "https://www.google.com.ca";

    public CountryDto ukraine() {
        var result = new CountryDto();
        result.setIso(UA_ISO);
        result.setName(UA_NAME);
        result.setLocaleGoogle(UA_LOCALE_GOOGLE);
        result.setLocaleYandex(UA_LOCALE_YANDEX);

        return result;
    }

    public CountryDto canada() {
        var result = new CountryDto();
        result.setIso(CA_ISO);
        result.setName(CA_NAME);
        result.setLocaleGoogle(CA_LOCALE_GOOGLE);

        return result;
    }
}
