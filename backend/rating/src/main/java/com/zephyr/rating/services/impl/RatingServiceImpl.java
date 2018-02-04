package com.zephyr.rating.services.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.data.dto.SearchCriteriaDto;
import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.events.RatingUpdatedEvent;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.services.RatingService;
import com.zephyr.rating.services.dto.RatingDto;
import com.zephyr.rating.services.dto.factory.RatingDtoFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RatingServiceImpl implements RatingService {
    private static final String NEW_SEARCH_RESULT_MESSAGE = "Received new Search result: {}";

    @Setter(onMethod = @__(@Autowired))
    private RatingRepository ratingRepository;

    @Setter(onMethod = @__(@Autowired))
    private RatingDtoFactory ratingDtoFactory;

    @Setter(onMethod = @__(@Autowired))
    private ApplicationEventPublisher applicationEventPublisher;

    @Setter(onMethod = @__(@Autowired))
    private Transformer<SearchResultDto, Iterable<Rating>> searchResultTransformer;

    @Setter(onMethod = @__(@Autowired))
    private Transformer<SearchCriteriaDto, Rating> searchCriteriaTransformer;

    @Override
    public Mono<Void> handleSearchResult(Flux<SearchResultDto> searchResult) {
        return searchResult.doOnNext(LoggingUtils.info(log, SearchResultDto::getId, NEW_SEARCH_RESULT_MESSAGE))
                .map(searchResultTransformer::transform)
                .flatMap(ratingRepository::saveAll)
                .doOnNext(r -> applicationEventPublisher.publishEvent(RatingUpdatedEvent.of(r.getQuery())))
                .then();
    }

    @Override
    public Mono<RatingDto> findRatingForSearchCriteria(SearchCriteriaDto searchCriteria) {
        return Mono.just(searchCriteria)
                .map(searchCriteriaTransformer::transform)
                .map(Example::of)
                .flatMapMany(ratingRepository::findAll)
                .collectList()
                .map(l -> ratingDtoFactory.create(searchCriteria, l));
    }
}