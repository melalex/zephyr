package com.zephyr.rating.services.impl;

import com.zephyr.data.protocol.dto.KeywordDto;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.domain.vo.SearchResultVo;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.services.RatingNotificationService;
import com.zephyr.rating.services.RatingService;
import com.zephyr.rating.support.PageableFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private RatingNotificationService ratingNotificationService;
    private PageableFactory pageableFactory;

    @Override
    public Flux<KeywordDto> findRatingForUrl(String url, int page, int size) {
        return ratingRepository.findAllByUrlStartsWith(url, pageableFactory.create(page, size))
                .map(KeywordDto::new);
    }

    @Override
    @ServiceActivator
    public void save(SearchResultVo target) {
        Mono.just(target)
                .flatMap(this::saveRating)
                .subscribe(ratingNotificationService::publishRatingUpdatedEvent);
    }

    private Mono<Request> saveRating(SearchResultVo target) {
        return Flux.fromIterable(target.getLinks())
                .map(r -> new Rating(target.getRequest(), r.getElement(), r.getIndex()))
                .flatMap(ratingRepository::save)
                .then(Mono.just(target.getRequest()));
    }
}