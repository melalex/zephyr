package com.zephyr.rating.repository;

import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.RatingCriteria;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface RatingOperations {

    Flux<Rating> findByUrl(String url, Pageable pageable);

    Flux<Rating> findByCriteria(RatingCriteria ratingCriteria);
}
