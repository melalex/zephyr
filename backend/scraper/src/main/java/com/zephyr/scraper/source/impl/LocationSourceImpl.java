package com.zephyr.scraper.source.impl;

import com.zephyr.data.dto.CountryDto;
import com.zephyr.data.dto.PlaceDto;
import com.zephyr.scraper.source.LocationSource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class LocationSourceImpl implements LocationSource {

    @Override
    public Mono<CountryDto> findCountry(String iso) {
        return null;
    }

    @Override
    public Mono<PlaceDto> findPlace(String iso, String name) {
        return null;
    }
}