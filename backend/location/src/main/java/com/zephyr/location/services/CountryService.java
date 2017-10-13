package com.zephyr.location.services;

import com.zephyr.data.CountryDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

public interface CountryService {

    Flux<CountryDto> getSupportedCountries(Locale locale);

    Mono<CountryDto> findByIsoCode(String iso, Locale locale);
}
