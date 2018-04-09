package com.zephyr.scraper.data;

import com.zephyr.scraper.domain.Query;
import com.zephyr.test.Countries;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class ScraperCountries {

    private Countries countries;
    private ModelMapper modelMapper;

    public Query.Country ukraine() {
        return modelMapper.map(countries.ukraine(), Query.Country.class);
    }
}
