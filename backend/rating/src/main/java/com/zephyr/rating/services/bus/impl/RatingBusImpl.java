package com.zephyr.rating.services.bus.impl;

import com.zephyr.commons.interfaces.Matcher;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.RatingCriteria;
import com.zephyr.rating.services.bus.RatingBus;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class RatingBusImpl implements RatingBus {
    private static final String RECEIVED_EVENT_MESSAGE = "Received new Rating: {}";

    private UnicastProcessor<Rating> hotSource;
    private Flux<Rating> hotFlux;

    @Setter(onMethod = @__(@Autowired))
    private Matcher<RatingCriteria, Rating> queryMatcher;

    @PostConstruct
    public void init() {
        hotSource = UnicastProcessor.create();
        hotFlux = hotSource.publish().autoConnect();
    }

    @Override
    public void onRatingUpdated(Rating rating) {
        log.info(RECEIVED_EVENT_MESSAGE, rating.getId());
        hotSource.onNext(rating);
    }

    @Override
    public Flux<RatingCriteria> updatesFor(RatingCriteria ratingCriteria) {
        return hotFlux.filter(q -> queryMatcher.matches(ratingCriteria, q))
                .map(r -> ratingCriteria);
    }
}
