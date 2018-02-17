package com.zephyr.rating.services;

import com.zephyr.data.protocol.dto.RatingDto;
import com.zephyr.data.protocol.dto.StatisticsDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface RatingService {

    Flux<RatingDto> findRatingForUrl(String url, Pageable pageable);

    Flux<StatisticsDto> findStatisticsAndSubscribeForTask(String task);
}