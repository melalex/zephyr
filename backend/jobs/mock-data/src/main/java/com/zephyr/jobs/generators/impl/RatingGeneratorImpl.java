package com.zephyr.jobs.generators.impl;

import com.zephyr.jobs.generators.RatingGenerator;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import lombok.Setter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
public class RatingGeneratorImpl implements RatingGenerator {

    @Setter(onMethod = @__(@Value("#{jobParameters[T(com.zephyr.jobs.batch.BatchConfiguration).RATING_PER_REQUEST_JOB_PARAM]}")))
    private int ratingPerRequest;

    @Override
    public List<Rating> generate(Request request) {
        return null;
    }
}
