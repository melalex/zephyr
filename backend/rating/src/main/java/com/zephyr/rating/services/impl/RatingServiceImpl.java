package com.zephyr.rating.services.impl;

import com.zephyr.data.dto.RatingDto;
import com.zephyr.data.forms.RatingForm;
import com.zephyr.mapping.mappers.ExtendedMapper;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.services.RatingService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RatingServiceImpl implements RatingService {

    @Setter(onMethod = @__(@Autowired))
    private RatingRepository ratingRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Mono<RatingDto> create(RatingForm rating) {
        return Mono.just(rating)
                .map(mapper.mapperFor(Rating.class))
                .flatMap(r -> ratingRepository.save(r))
                .map(mapper.mapperFor(RatingDto.class));
    }

    @Override
    public Flux<RatingDto> findByUserId(String id) {
        return ratingRepository.findAllByUser(id)
                .map(mapper.mapperFor(RatingDto.class));
    }

    @Override
    public Mono<Void> update(RatingDto rating) {
        return Mono.just(rating)
                .map(mapper.mapperFor(Rating.class))
                .flatMap(r -> ratingRepository.save(r))
                .then();
    }
}