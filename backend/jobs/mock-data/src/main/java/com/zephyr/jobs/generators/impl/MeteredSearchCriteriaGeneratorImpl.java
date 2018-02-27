package com.zephyr.jobs.generators.impl;

import com.github.javafaker.Faker;
import com.zephyr.jobs.generators.DateTimeGenerator;
import com.zephyr.jobs.generators.MeteredSearchCriteriaGenerator;
import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeteredSearchCriteriaGeneratorImpl implements MeteredSearchCriteriaGenerator {
    private static final int MAX_HIT_COUNT = 100;

    @Setter(onMethod = @__(@Autowired))
    private Faker faker;

    @Setter(onMethod = @__(@Autowired))
    private DateTimeGenerator dateTimeGenerator;

    @Override
    public MeteredSearchCriteria generate(SearchCriteria criteria) {
        MeteredSearchCriteria result = new MeteredSearchCriteria();
        result.setSearchCriteria(criteria);
        result.setLastHit(dateTimeGenerator.generateDateTime());
        result.setLastUpdate(dateTimeGenerator.generateDateTime());
        result.setHitsCount(faker.random().nextInt(MAX_HIT_COUNT));

        return result;
    }
}
