package com.zephyr.rating.controllers;

import com.zephyr.data.dto.TaskDto;
import com.zephyr.rating.services.RatingService;
import com.zephyr.rating.services.dto.RatingDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("/rating")
public class RatingController {

    @Setter(onMethod = @__(@Autowired))
    private RatingService ratingService;

    @GetMapping
    public Flux<RatingDto> getRatingForTask(Mono<TaskDto> task) {
        return ratingService.findRatingForTask(task);
    }
}