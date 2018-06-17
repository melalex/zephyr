package com.zephyr.rating.services.impl;

import static java.util.Objects.isNull;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

import com.zephyr.commons.interfaces.Assembler;
import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.data.protocol.request.StatisticRequest;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.domain.RequestCriteria;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.services.StatisticService;
import com.zephyr.rating.services.SubscriptionService;
import com.zephyr.rating.support.StatisticsDtoFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private static final String NEW_SUBSCRIPTION_MESSAGE = "New subscription to Task [ {} ]";

    private RatingRepository ratingRepository;
    private StatisticsDtoFactory statisticsDtoFactory;
    private SubscriptionService subscriptionService;
    private Assembler<StatisticRequest, List<RequestCriteria>> requestCriteriaAssembler;

    @Override
    public Flux<StatisticsDto> findStatisticsAndSubscribeForTask(StatisticRequest request) {
        log.info(NEW_SUBSCRIPTION_MESSAGE, request.getTaskId());

        return requestCriteriaAssembler.assemble(request)
                .flatMapIterable(Function.identity())
                .flatMap(this::findStatisticsAndSubscribeForTask);
    }

    private Mono<StatisticsDto> findStatisticsAndSubscribeForTask(RequestCriteria criteria) {
        return ratingRepository.findByCriteria(criteria)
                .switchIfEmpty(isNull(criteria.getTo()) ? subscribe(criteria) : Flux.empty())
                .collectList()
                .map(l -> statisticsDtoFactory.create(criteria, l))
                .log();
    }

    private Flux<Rating> subscribe(RequestCriteria criteria) {
        return subscriptionService.subscribe(criteria)
                .flatMap(r -> ratingRepository.findAll(ratingExample(criteria, r)));
    }

    @NotNull
    private Example<Rating> ratingExample(RequestCriteria criteria, Request r) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher(Rating.URL_FIELD, contains());

        return Example.of(new Rating(r, criteria.getUrl()), exampleMatcher);
    }
}
