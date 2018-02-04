package com.zephyr.rating.controllers;

import com.zephyr.data.dto.TaskDto;
import com.zephyr.rating.services.SubscriptionService;
import com.zephyr.rating.services.dto.RatingDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("/rating")
public class RatingController {

    @Setter(onMethod = @__(@Autowired))
    private SubscriptionService subscriptionService;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<RatingDto> getRatingForTask(Mono<TaskDto> task) {
        return subscriptionService.subscribe(task);
    }
}