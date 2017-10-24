package com.zephyr.scraper.source;

import com.zephyr.data.dto.CountryDto;
import com.zephyr.data.dto.PlaceDto;
import reactor.core.publisher.Mono;

public interface LocationSource {

    Mono<CountryDto> findCountry(String iso);

    Mono<PlaceDto> findPlace(String iso, String name);
}