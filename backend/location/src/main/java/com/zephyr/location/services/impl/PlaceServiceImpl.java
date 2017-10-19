package com.zephyr.location.services.impl;

import com.zephyr.data.dto.PlaceDto;
import com.zephyr.errors.utils.ErrorUtil;
import com.zephyr.location.domain.Place;
import com.zephyr.location.repositories.PlaceRepository;
import com.zephyr.location.services.PlaceService;
import com.zephyr.mapping.mappers.ExtendedMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class PlaceServiceImpl implements PlaceService {

    @Setter(onMethod = @__(@Autowired))
    private PlaceRepository placeRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Mono<PlaceDto> findById(long id) {
        return placeRepository.findById(id)
                .map(mapper.mapperFor(PlaceDto.class))
                .switchIfEmpty(ErrorUtil.notFound(Place.class, id));
    }

    @Override
    public Mono<PlaceDto> findByCountryIsoAndName(String iso, String name) {
        return placeRepository.findByCountryIsoAndName(iso, name)
                .map(mapper.mapperFor(PlaceDto.class))
                .switchIfEmpty(ErrorUtil.notFound(Place.class, iso));
    }

    @Override
    public Flux<PlaceDto> findByCountryIsoAndNameStartsWith(String iso, String name) {
        return placeRepository.findAllByCountryIsoAndNameStartingWith(iso, name)
                .map(mapper.mapperFor(PlaceDto.class))
                .switchIfEmpty(ErrorUtil.notFound(Place.class, iso));
    }

    @Override
    public Flux<PlaceDto> findByCountryIso(String iso, Pageable pageable) {
        return placeRepository.findAllByCountryIso(iso, pageable)
                .map(mapper.mapperFor(PlaceDto.class))
                .switchIfEmpty(ErrorUtil.notFound(Place.class, iso));
    }
}
