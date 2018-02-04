package com.zephyr.rating.services.bus.impl;

import com.zephyr.commons.ObjectUtils;
import com.zephyr.data.dto.SearchCriteriaDto;
import com.zephyr.rating.domain.Query;
import com.zephyr.rating.events.RatingUpdatedEvent;
import com.zephyr.rating.services.RatingService;
import com.zephyr.rating.services.bus.RatingBus;
import com.zephyr.rating.services.dto.RatingDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
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

    @PostConstruct
    public void init() {
        hotSource = UnicastProcessor.create();
        hotFlux = hotSource.publish().autoConnect();
    }

    @EventListener
    public void onRatingUpdated(RatingUpdatedEvent event) {
        log.info(RECEIVED_EVENT_MESSAGE, event.getQuery());
        hotSource.onNext(event.getQuery());
    }

    @Override
    public Flux<RatingDto> updatesFor(SearchCriteriaDto searchCriteria) {
        return hotFlux.filter(q -> isSubscribed(q, searchCriteria))
                .flatMap(q -> ratingService.findRatingForSearchCriteria(searchCriteria));
    }

    private boolean isSubscribed(Query query, SearchCriteriaDto criteria) {
        return ObjectUtils.equalsOrNull(criteria.getQuery(), query.getQuery())
                && ObjectUtils.equalsOrNull(criteria.getLanguageIso(), query.getLanguageIso())
                && ObjectUtils.equalsOrNull(criteria.getPlace().getCountry(), query.getPlace().getCountry())
                && ObjectUtils.equalsOrNull(criteria.getPlace().getPlaceName(), query.getPlace().getPlaceName())
                && ObjectUtils.equalsOrNull(criteria.getUserAgent().getOsName(), query.getUserAgent().getOsName())
                && ObjectUtils.equalsOrNull(criteria.getUserAgent().getOsVersion(), query.getUserAgent().getOsVersion())
                && ObjectUtils.equalsOrNull(criteria.getUserAgent().getBrowserName(), query.getUserAgent().getBrowserName())
                && ObjectUtils.equalsOrNull(criteria.getUserAgent().getBrowserVersion(), query.getUserAgent().getBrowserVersion());
    }
}
