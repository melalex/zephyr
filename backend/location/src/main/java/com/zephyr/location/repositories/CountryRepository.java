package com.zephyr.location.repositories;

import com.zephyr.location.domain.Country;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CountryRepository extends ReactiveMongoRepository<Country, String> {

    Mono<Country> findOneByIso2(String iso2);
}