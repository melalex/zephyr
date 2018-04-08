package com.zephyr.location.core.repositories;

import com.zephyr.location.core.domain.Country;
import org.springframework.data.neo4j.annotation.Depth;

import java.util.Optional;
import java.util.stream.Stream;

public interface CountryRepository extends FunctionalNeo4jRepository<Country, String> {

    Optional<Country> findByIso(String iso);

    @Depth(0)
    Stream<Country> findByNameStartingWith(String name);
}
