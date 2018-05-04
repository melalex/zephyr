package com.zephyr.location.services;

import com.zephyr.data.protocol.dto.PlaceDto;

import java.util.Set;

public interface PlaceService {

    PlaceDto findById(long id);

    Set<PlaceDto> findAllByCountryIsoAndNameContains(String iso, String name);
}