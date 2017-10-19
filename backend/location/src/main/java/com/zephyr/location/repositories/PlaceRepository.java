package com.zephyr.location.repositories;

import com.zephyr.location.domain.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlaceRepository extends ReactiveMongoRepository<Place, Long> {

    Mono<Place> findByCountryIsoAndName(String iso, String name);

    Flux<Place> findAllByCountryIso(String iso, Pageable pageable);

    Flux<Place> findAllByCountryIsoAndNameStartingWith(String iso, String name);
}