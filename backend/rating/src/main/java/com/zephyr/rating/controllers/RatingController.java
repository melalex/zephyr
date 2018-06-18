package com.zephyr.rating.controllers;

import com.zephyr.data.protocol.dto.KeywordDto;
import com.zephyr.rating.services.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/rating")
public class RatingController {

    private RatingService ratingService;

    @GetMapping
    public Flux<KeywordDto> findRatingForUrl(@RequestParam String url,
                                             @RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "20") int size) {
        return ratingService.findRatingForUrl(url, page, size);
    }
}