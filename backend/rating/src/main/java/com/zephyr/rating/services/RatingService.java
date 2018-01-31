package com.zephyr.rating.services;

import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.data.dto.TaskDto;
import com.zephyr.rating.services.dto.RatingDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RatingService {

    Mono<Void> handleSearchResult(Flux<SearchResultDto> searchResult);

    Flux<RatingDto> findRatingForTask(Mono<TaskDto> searchResult);
}