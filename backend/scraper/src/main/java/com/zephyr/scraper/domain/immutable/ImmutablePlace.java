package com.zephyr.scraper.domain.immutable;

import com.zephyr.data.dto.CountryDto;
import com.zephyr.data.enums.PlaceType;

@SuppressWarnings("unused")
public interface ImmutablePlace {

    String getLocation();

    PlaceType getType();

    CountryDto getCountry();

    long getParent();

    String getCanonicalName();

    String getName();

    long getId();
}
