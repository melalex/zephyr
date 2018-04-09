package com.zephyr.scraper.data;

import com.zephyr.scraper.domain.Query;
import com.zephyr.test.Places;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class ScraperPlaces {

    private Places places;
    private ModelMapper modelMapper;

    public Query.Place kiev() {
        return modelMapper.map(places, Query.Place.class);
    }
}
