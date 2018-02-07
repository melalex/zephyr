package com.zephyr.rating.services;

import com.zephyr.data.dto.RatingDto;
import com.zephyr.data.dto.TaskDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubscriptionService {

    Flux<RatingDto> subscribe(Mono<TaskDto> task);
}
