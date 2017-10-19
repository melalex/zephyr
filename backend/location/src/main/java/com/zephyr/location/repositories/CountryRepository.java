package com.zephyr.location.repositories;

import com.zephyr.location.domain.Country;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryRepository extends ReactiveMongoRepository<Country, String> {

    Flux<Country> findAllByNameStartingWith(String name);
}