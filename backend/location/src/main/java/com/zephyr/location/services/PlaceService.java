package com.zephyr.location.services;

import com.zephyr.data.protocol.dto.PlaceDto;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface PlaceService {

    PlaceDto findById(long id);

    Set<PlaceDto> findAllByCountryIsoAndNameContains(String iso, String name);

    Set<PlaceDto> findAllByCanonicalNameContains(String name, Pageable pageable);
}