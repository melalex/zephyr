package com.zephyr.rating.repository;

import com.zephyr.rating.domain.Rating;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RatingRepository extends ReactiveMongoRepository<Rating, String>, RatingOperations {

}