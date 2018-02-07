package com.zephyr.rating.services.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.data.dto.RatingDto;
import com.zephyr.data.dto.TaskDto;
import com.zephyr.rating.cliensts.TaskServiceClient;
import com.zephyr.rating.domain.RatingCriteria;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.services.RatingService;
import com.zephyr.rating.services.bus.RatingBus;
import com.zephyr.rating.services.dto.StatisticsDto;
import com.zephyr.rating.services.dto.factory.RatingDtoFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RatingServiceImpl implements RatingService {
    private static final String NEW_SUBSCRIPTION_MESSAGE = "New RatingCriteria: {}";

    @Setter(onMethod = @__(@Autowired))
    private RatingBus ratingBus;

    @Setter(onMethod = @__(@Autowired))
    private RatingRepository ratingRepository;

    @Setter(onMethod = @__(@Autowired))
    private TaskServiceClient taskServiceClient;

    @Setter(onMethod = @__(@Autowired))
    private RatingDtoFactory ratingDtoFactory;

    @Setter(onMethod = @__(@Autowired))
    private Transformer<TaskDto, Iterable<RatingCriteria>> taskTransformer;

    @Override
    public Flux<RatingDto> findRatingForUrl(String url, Pageable pageable) {
        return null;
    }

    @Override
    public Flux<StatisticsDto> findStatisticsAndSubscribeForTask(String task) {
        return taskServiceClient.findById(task)
                .flatMapIterable(taskTransformer::transform)
                .doOnNext(LoggingUtils.info(log, NEW_SUBSCRIPTION_MESSAGE))
                .flatMap(this::findStatisticsAndSubscribeForTask);
    }

    private Flux<StatisticsDto> findStatisticsAndSubscribeForTask(RatingCriteria ratingCriteria) {
        return findStatisticsForExample(ratingCriteria)
                .concatWith(ratingBus.updatesFor(ratingCriteria).flatMap(this::findStatisticsForExample));
    }

    private Mono<StatisticsDto> findStatisticsForExample(RatingCriteria ratingCriteria) {
        return ratingRepository.findByCriteria(ratingCriteria)
                .collectList()
                .map(l -> ratingDtoFactory.create(ratingCriteria, l));
    }
}