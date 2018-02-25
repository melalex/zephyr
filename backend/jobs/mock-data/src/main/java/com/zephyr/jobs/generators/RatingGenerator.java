package com.zephyr.jobs.generators;

import com.zephyr.rating.domain.Rating;
import com.zephyr.task.domain.SearchCriteria;

@FunctionalInterface
public interface RatingGenerator {

    Rating generate(SearchCriteria criteria);
}
