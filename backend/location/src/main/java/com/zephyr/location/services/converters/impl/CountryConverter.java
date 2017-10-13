package com.zephyr.location.services.converters.impl;

import com.zephyr.data.CountryDto;
import com.zephyr.location.domain.Country;
import com.zephyr.location.services.converters.LocalizingConverter;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class CountryConverter implements LocalizingConverter<Country, CountryDto> {
    private static final String DEFAULT_GOOGLE_URL = "google.com";

    @Override
    public CountryDto convert(Country country, Locale locale) {
        CountryDto result = new CountryDto();

        result.setIso(country.getIso2());
        result.setLocaleGoogle(Optional.ofNullable(country.getLocaleGoogle()).orElse(DEFAULT_GOOGLE_URL));
        result.setName(country.getName().getOrDefault(locale.getLanguage(), Locale.ENGLISH.getLanguage()));

        return result;
    }
}