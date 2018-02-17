package com.zephyr.rating.services.impl;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.data.protocol.dto.RatingDto;
import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.rating.cliensts.TaskServiceClient;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.RequestCriteria;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.services.RatingService;
import com.zephyr.rating.services.bus.RequestUpdatesBus;
import com.zephyr.rating.services.factories.RatingDtoFactory;
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
    private static final String NEW_SUBSCRIPTION_MESSAGE = "New subscription to Task '{}'";

    @Setter(onMethod = @__(@Autowired))
    private RequestUpdatesBus requestUpdatesBus;

    @Setter(onMethod = @__(@Autowired))
    private RatingRepository ratingRepository;

    @Setter(onMethod = @__(@Autowired))
    private TaskServiceClient taskServiceClient;

    @Setter(onMethod = @__(@Autowired))
    private RatingDtoFactory ratingDtoFactory;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Setter(onMethod = @__(@Autowired))
    private Transformer<TaskDto, Iterable<RequestCriteria>> taskTransformer;

    @Override
    public Flux<RatingDto> findRatingForUrl(String url, Pageable pageable) {
        return ratingRepository.findAllByUrl(url, pageable)
                .map(mapper.mapperFor(RatingDto.class));
    }

    @Override
    public Flux<StatisticsDto> findStatisticsAndSubscribeForTask(String task) {
        log.info(NEW_SUBSCRIPTION_MESSAGE, task);

        return taskServiceClient.findById(task)
                .flatMapIterable(taskTransformer::transform)
                .flatMap(this::findStatisticsAndSubscribeForTask);
    }

    private Flux<StatisticsDto> findStatisticsAndSubscribeForTask(RequestCriteria criteria) {
        return toStatistic(ratingRepository.findByCriteria(criteria), criteria)
                .concatWith(subscribe(criteria));
    }

    private Flux<StatisticsDto> subscribe(RequestCriteria criteria) {
        return requestUpdatesBus.updatesFor(criteria)
                .map(r -> ratingRepository.findAllByRequestIdAndUrl(r.getId(), criteria.getUrl()))
                .flatMap(f -> toStatistic(f, criteria));
    }

    private Mono<StatisticsDto> toStatistic(Flux<Rating> rating, RequestCriteria criteria) {
        return rating.collectList()
                .map(l -> ratingDtoFactory.create(criteria, l));
    }
}