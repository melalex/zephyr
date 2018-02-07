package com.zephyr.rating.controllers;

import com.zephyr.data.dto.RatingDto;
import com.zephyr.rating.services.RatingService;
import com.zephyr.rating.services.dto.StatisticsDto;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController("/v1/rating")
public class RatingController {

    @Setter(onMethod = @__(@Autowired))
    private RatingService ratingService;

    @GetMapping
    public Flux<RatingDto> findRatingForUrl(@Valid @URL String url, Pageable pageable) {
        return ratingService.findRatingForUrl(url, pageable);
    }

    @GetMapping(path = "/{taskId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<StatisticsDto> findStatisticsAndSubscribeForTask(@PathVariable("taskId") String taskId) {
        return ratingService.findStatisticsAndSubscribeForTask(taskId);
    }
}