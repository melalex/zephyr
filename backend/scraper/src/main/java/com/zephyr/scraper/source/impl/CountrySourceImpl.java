package com.zephyr.scraper.source.impl;

import com.zephyr.scraper.domain.Country;
import com.zephyr.scraper.source.CountrySource;
import org.springframework.stereotype.Component;

@Component
public class CountrySourceImpl implements CountrySource {

    @Override
    public Country find(String iso, String place) {
        return null;
    }
}