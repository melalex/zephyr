package com.zephyr.rating.repository;

import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.RequestCriteria;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface RatingOperations {

    Flux<Rating> findAllByUrl(String url, Pageable pageable);

    Flux<Rating> findByCriteria(RequestCriteria requestCriteria);
}
