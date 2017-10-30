package com.zephyr.personalisation.services;

import com.zephyr.data.dto.CountryDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryService {

    Mono<CountryDto> findByIso(String iso);

    Flux<CountryDto> findAll();

    Flux<CountryDto> findByNameStarts(String name);
}
