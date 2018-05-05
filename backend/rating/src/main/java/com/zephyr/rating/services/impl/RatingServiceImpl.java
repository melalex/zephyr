package com.zephyr.rating.services.impl;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.protocol.dto.RatingDto;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.domain.vo.SearchResultVo;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.repository.RequestRepository;
import com.zephyr.rating.services.RatingNotificationService;
import com.zephyr.rating.services.RatingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private ExtendedMapper mapper;
    private RequestRepository requestRepository;
    private RatingNotificationService ratingNotificationService;

    @Override
    public Flux<RatingDto> findRatingForUrl(String url, Pageable pageable) {
        return ratingRepository.findAllByUrl(url, pageable)
                .map(mapper.mapperFor(RatingDto.class));
    }

    @Override
    @ServiceActivator
    public void save(SearchResultVo target) {
        Mono.just(target)
                .flatMap(findOrSave())
                .flatMap(this::saveRating)
                .subscribe(ratingNotificationService::publishRatingUpdatedEvent);
    }

    private Function<SearchResultVo, Mono<SearchResultVo>> findOrSave() {
        return s -> requestRepository.findOne(Example.of(s.getRequest()))
                .switchIfEmpty(requestRepository.save(s.getRequest()))
                .map(r -> SearchResultVo.of(r, s.getLinks()));
    }

    private Mono<Request> saveRating(SearchResultVo target) {
        return Flux.fromIterable(target.getLinks())
                .map(r -> new Rating(target.getRequest(), r.getIndex(), r.getElement()))
                .flatMap(ratingRepository::save)
                .then(Mono.just(target.getRequest()));
    }
}