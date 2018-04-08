package com.zephyr.location.core.repositories;

import com.zephyr.location.core.domain.Place;

import java.util.stream.Stream;

public interface PlaceRepository extends FunctionalNeo4jRepository<Place, Long> {

    Stream<Place> findAllByCountryIsoAndNameStartingWith(String iso, String name);
}