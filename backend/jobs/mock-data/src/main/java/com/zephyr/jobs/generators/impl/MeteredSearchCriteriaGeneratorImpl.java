package com.zephyr.jobs.generators.impl;

import com.zephyr.jobs.extensions.ExtendedFaker;
import com.zephyr.jobs.generators.MeteredSearchCriteriaGenerator;
import com.zephyr.jobs.properties.MockDataProperties;
import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

@Component
public class MeteredSearchCriteriaGeneratorImpl implements MeteredSearchCriteriaGenerator {
    private static final int MAX_HIT_COUNT = 100;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedFaker faker;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Setter(onMethod = @__(@Autowired))
    private MockDataProperties properties;

    @Override
    public MeteredSearchCriteria generate(SearchCriteria criteria) {
        MeteredSearchCriteria result = new MeteredSearchCriteria();
        LocalDate now = LocalDate.now(clock);
        LocalDate launchDate = properties.getLaunchDate(now);

        result.setSearchCriteria(criteria);
        result.setLastHit(faker.time().dateTime(now, launchDate));
        result.setLastUpdate(faker.time().dateTime(now, launchDate));
        result.setHitsCount(faker.random().nextInt(MAX_HIT_COUNT));

        return result;
    }
}
