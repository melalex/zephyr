package com.zephyr.rating.controllers;

import com.zephyr.data.protocol.dto.RatingDto;
import com.zephyr.rating.services.RatingService;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/rating")
public class RatingController {

    private RatingService ratingService;

    @GetMapping
    public Flux<RatingDto> findRatingForUrl(@Valid @URL String url, Pageable pageable) {
        return ratingService.findRatingForUrl(url, pageable);
    }
}