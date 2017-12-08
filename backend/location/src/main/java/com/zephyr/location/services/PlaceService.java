package com.zephyr.location.services;

import com.zephyr.data.dto.PlaceDto;

import java.util.Set;

public interface PlaceService {

    PlaceDto findById(long id);

    Set<PlaceDto> findByCountryIsoAndNameStartsWith(String iso, String name);
}