package com.zephyr.location.services;

import com.zephyr.data.dto.PlaceDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlaceService {

    Mono<PlaceDto> findById(long id);

    Mono<PlaceDto> findByCountryIsoAndName(String iso, String name);

    Flux<PlaceDto> findByCountryIsoAndNameStartsWith(String iso, String name);

    Flux<PlaceDto> findByCountryIso(String iso, Pageable pageable);
}