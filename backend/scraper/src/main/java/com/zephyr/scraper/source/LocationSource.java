package com.zephyr.scraper.source;

import com.zephyr.data.dto.PlaceDto;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface LocationSource {

    Mono<PlaceDto> findPlace(String iso, String name);
}