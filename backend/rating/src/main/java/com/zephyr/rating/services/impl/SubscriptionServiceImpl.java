package com.zephyr.rating.services.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.data.dto.SearchCriteriaDto;
import com.zephyr.data.dto.TaskDto;
import com.zephyr.rating.services.RatingService;
import com.zephyr.rating.services.SubscriptionService;
import com.zephyr.rating.services.bus.RatingBus;
import com.zephyr.rating.services.dto.RatingDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private static final String NEW_SUBSCRIPTION_MESSAGE = "New Subscription to Search criteria: {}";

    @Setter(onMethod = @__(@Autowired))
    private RatingService ratingService;

    @Setter(onMethod = @__(@Autowired))
    private RatingBus ratingBus;

    @Override
    public Flux<RatingDto> subscribe(Mono<TaskDto> task) {
        return task.map(TaskDto::getSearchCriteria)
                .flatMapMany(Flux::fromIterable)
                .doOnNext(LoggingUtils.info(log, SearchCriteriaDto::getId, NEW_SUBSCRIPTION_MESSAGE))
                .flatMap(this::subscribe);
    }

    private Flux<RatingDto> subscribe(SearchCriteriaDto searchCriteria) {
        return ratingService.findRatingForSearchCriteria(searchCriteria)
                .concatWith(ratingBus.updatesFor(searchCriteria));
    }
}
