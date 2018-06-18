package com.zephyr.location.repositories;

import com.zephyr.location.domain.Place;
import org.springframework.data.domain.Pageable;

import java.util.stream.Stream;

public interface PlaceRepository extends FunctionalNeo4jRepository<Place, Long> {

    Stream<Place> findAllByCountryIsoAndNameContainsIgnoreCase(String iso, String name);

    Stream<Place> findAllByCanonicalNameContains(String name, Pageable pageable);
}