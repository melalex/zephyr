package com.zephyr.rating.core.repository;

import com.zephyr.rating.core.domain.Rating;
import com.zephyr.rating.core.domain.RequestCriteria;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface RatingOperations {

    Flux<Rating> findAllByUrl(String url, Pageable pageable);

    Flux<Rating> findByCriteria(RequestCriteria requestCriteria);
}
