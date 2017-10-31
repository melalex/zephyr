package com.zephyr.rating.controllers;

import com.zephyr.data.dto.RatingDto;
import com.zephyr.data.forms.RatingForm;
import com.zephyr.rating.services.RatingService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RatingController {

    @Setter(onMethod = @__(@Autowired))
    private RatingService ratingService;

    @PostMapping
    public Mono<RatingDto> create(RatingForm rating) {
        return ratingService.create(rating);
    }

    @GetMapping
    public Flux<RatingDto> findByUserId(String id) {
        return ratingService.findByUserId(id);
    }
}