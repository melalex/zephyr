package com.zephyr.rating.services;

import com.zephyr.data.dto.RatingDto;
import com.zephyr.data.forms.RatingForm;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RatingService {

    Mono<RatingDto> create(RatingForm rating);

    Flux<RatingDto> findByUserId(String id);

    Mono<Void> update(RatingDto rating);
}