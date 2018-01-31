package com.zephyr.rating.services.impl;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.data.dto.TaskDto;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.services.RatingService;
import com.zephyr.rating.services.dto.RatingDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RatingServiceImpl implements RatingService {

    @Setter(onMethod = @__(@Autowired))
    private RatingRepository ratingRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Setter(onMethod = @__(@Autowired))
    private Transformer<SearchResultDto, Iterable<Rating>> searchResultTransformer;

    @Override
    public Mono<Void> handleSearchResult(Flux<SearchResultDto> searchResult) {
        return searchResult.map(searchResultTransformer::transform)
                .flatMap(ratingRepository::saveAll)
                .then();
    }

    @Override
    public Flux<RatingDto> findRatingForTask(Mono<TaskDto> searchResult) {
        return null;
    }
}