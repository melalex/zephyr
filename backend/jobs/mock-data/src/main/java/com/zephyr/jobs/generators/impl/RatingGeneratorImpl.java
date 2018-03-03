package com.zephyr.jobs.generators.impl;

import com.zephyr.jobs.extensions.ExtendedFaker;
import com.zephyr.jobs.generators.RatingGenerator;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import lombok.Setter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@StepScope
public class RatingGeneratorImpl implements RatingGenerator {

    @Setter(onMethod = @__(@Value("#{jobParameters[T(com.zephyr.jobs.batch.BatchConfiguration).RATING_PER_REQUEST_JOB_PARAM]}")))
    private int ratingPerRequest;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedFaker faker;

    @Override
    public List<Rating> generate(Request request) {
        return IntStream.rangeClosed(1, ratingPerRequest)
                .mapToObj(i -> generateRating(i, request))
                .collect(Collectors.toList());
    }

    private Rating generateRating(int index, Request request) {
        Rating rating = new Rating();
        rating.setRequest(request);
        rating.setPosition(index);
        rating.setUrl(faker.internet().url());

        return rating;
    }
}
