package com.zephyr.location.services.impl;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.dto.PlaceDto;
import com.zephyr.errors.utils.ErrorUtil;
import com.zephyr.location.domain.Place;
import com.zephyr.location.repositories.PlaceRepository;
import com.zephyr.location.services.PlaceService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {
    private static final int DEFAULT_DEPTH = 1;

    @Setter(onMethod = @__(@Autowired))
    private PlaceRepository placeRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public PlaceDto findById(long id) {
        return placeRepository.findById(id, DEFAULT_DEPTH)
                .map(mapper.mapperFor(PlaceDto.class))
                .orElseThrow(ErrorUtil.newNotFoundError(Place.class, id));
    }

    @Override
    public Set<PlaceDto> findByCountryIsoAndNameStartsWith(String iso, String name) {
        return placeRepository.findAllByCountryIsoAndNameStartingWith(iso, name)
                .map(mapper.mapperFor(PlaceDto.class))
                .collect(Collectors.toSet());
    }
}
