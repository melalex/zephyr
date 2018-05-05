package com.zephyr.rating.services;

import com.zephyr.data.protocol.dto.RatingDto;
import com.zephyr.rating.domain.vo.SearchResultVo;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.annotation.ServiceActivator;
import reactor.core.publisher.Flux;

public interface RatingService {

    Flux<RatingDto> findRatingForUrl(String url, Pageable pageable);

    @ServiceActivator
    void save(SearchResultVo target);
}