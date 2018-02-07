package com.zephyr.rating.services.bus;

import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.RatingCriteria;
import reactor.core.publisher.Flux;

public interface RatingBus {

    void onRatingUpdated(Rating rating);

    Flux<RatingCriteria> updatesFor(RatingCriteria ratingCriteria);
}
