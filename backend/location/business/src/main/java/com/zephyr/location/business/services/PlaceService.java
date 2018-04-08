package com.zephyr.location.business.services;

import com.zephyr.data.protocol.dto.PlaceDto;

import java.util.Set;

public interface PlaceService {

    PlaceDto findById(long id);

    Set<PlaceDto> findByCountryIsoAndNameStartsWith(String iso, String name);
}