package com.zephyr.rating.services.bus.impl;

import com.zephyr.commons.interfaces.Matcher;
import com.zephyr.data.dto.SearchCriteriaDto;
import com.zephyr.rating.domain.Query;
import com.zephyr.rating.services.RatingService;
import com.zephyr.rating.services.bus.RatingBus;
import com.zephyr.rating.services.dto.RatingDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class RatingBusImpl implements RatingBus {
    private static final String RECEIVED_EVENT_MESSAGE = "Received new Query: {}";

    private UnicastProcessor<Query> hotSource;
    private Flux<Query> hotFlux;

    @Setter(onMethod = @__(@Autowired))
    private RatingService ratingService;

    @Setter(onMethod = @__(@Autowired))
    private Matcher<SearchCriteriaDto, Query> queryMatcher;

    @PostConstruct
    public void init() {
        hotSource = UnicastProcessor.create();
        hotFlux = hotSource.publish().autoConnect();
    }

    @Override
    public void onRatingUpdated(Query query) {
        log.info(RECEIVED_EVENT_MESSAGE, query);
        hotSource.onNext(query);
    }

    @Override
    public Flux<RatingDto> updatesFor(SearchCriteriaDto searchCriteria) {
        return hotFlux.filter(q -> queryMatcher.matches(searchCriteria, q))
                .flatMap(q -> ratingService.findRatingForSearchCriteria(searchCriteria));
    }
}
