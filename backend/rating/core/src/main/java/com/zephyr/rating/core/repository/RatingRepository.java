package com.zephyr.rating.core.repository;

import com.zephyr.rating.core.domain.Rating;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface RatingRepository extends ReactiveMongoRepository<Rating, String>, RatingOperations {

    Flux<Rating> findAllByRequestIdAndUrl(String id, String url);
}