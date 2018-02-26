package com.zephyr.jobs.generators;

import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;

import java.util.List;

@FunctionalInterface
public interface RatingGenerator {

    List<Rating> generate(Request request);
}
