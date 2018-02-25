package com.zephyr.jobs.generators.impl;

import com.zephyr.jobs.generators.RatingGenerator;
import com.zephyr.rating.domain.Rating;
import com.zephyr.task.domain.SearchCriteria;
import org.springframework.stereotype.Component;

@Component
public class RatingGeneratorImpl implements RatingGenerator {

    @Override
    public Rating generate(SearchCriteria criteria) {
        return null;
    }
}
