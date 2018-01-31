package com.zephyr.rating.controllers;

import com.zephyr.rating.services.RatingService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingController {

    @Setter(onMethod = @__(@Autowired))
    private RatingService ratingService;

}