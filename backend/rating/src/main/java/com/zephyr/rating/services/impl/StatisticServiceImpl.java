package com.zephyr.rating.services.impl;

import com.zephyr.commons.interfaces.Assembler;
import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.data.protocol.request.StatisticRequest;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.RequestCriteria;
import com.zephyr.rating.support.RatingDtoFactory;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.services.StatisticService;
import com.zephyr.rating.services.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.function.Function;

@Slf4j
@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private static final String NEW_SUBSCRIPTION_MESSAGE = "New subscription to Task '{}'";

    private SubscriptionService subscriptionService;
    private RatingRepository ratingRepository;
    private RatingDtoFactory ratingDtoFactory;
    private Assembler<StatisticRequest, Set<RequestCriteria>> requestCriteriaAssembler;

    @Override
    public Flux<StatisticsDto> findStatisticsAndSubscribeForTask(StatisticRequest request) {
        log.info(NEW_SUBSCRIPTION_MESSAGE, request.getTaskId());

        return requestCriteriaAssembler.assemble(request)
                .flatMapIterable(Function.identity())
                .flatMap(this::findStatisticsAndSubscribeForTask);
    }

    private Flux<StatisticsDto> findStatisticsAndSubscribeForTask(RequestCriteria criteria) {
        return toStatistic(ratingRepository.findByCriteria(criteria), criteria)
                .concatWith(subscribe(criteria));
    }

    private Flux<StatisticsDto> subscribe(RequestCriteria criteria) {
        return subscriptionService.subscribeOn(criteria)
                .map(r -> ratingRepository.findAllByRequestIdAndUrl(r.getId(), criteria.getUrl()))
                .flatMap(f -> toStatistic(f, criteria));
    }

    private Mono<StatisticsDto> toStatistic(Flux<Rating> rating, RequestCriteria criteria) {
        return rating.collectList()
                .map(l -> ratingDtoFactory.create(criteria, l));
    }
}
