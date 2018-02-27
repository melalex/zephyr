package com.zephyr.jobs.generators.impl;

import com.github.javafaker.Faker;
import com.zephyr.commons.TimeUtils;
import com.zephyr.jobs.generators.DateTimeGenerator;
import com.zephyr.jobs.properties.MockDataProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DateTimeGeneratorImpl implements DateTimeGenerator {

    @Setter(onMethod = @__(@Autowired))
    private Faker faker;

    @Setter(onMethod = @__(@Autowired))
    private MockDataProperties properties;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Override
    public LocalDate generateDate() {
        LocalDate now = LocalDate.now(clock);

        return TimeUtils.toLocalDate(faker.date().between(properties.getLaunchDate(now), TimeUtils.toDate(now)));
    }

    @Override
    public LocalDate generateDate(LocalDate to) {
        LocalDate now = LocalDate.now(clock);

        return TimeUtils.toLocalDate(faker.date().between(properties.getLaunchDate(now), TimeUtils.toDate(to)));
    }

    @Override
    public LocalDateTime generateDateTime() {
        return LocalDateTime.from(generateDate());
    }

    @Override
    public LocalDateTime generateDateTime(LocalDateTime to) {
        return LocalDateTime.from(generateDate(LocalDate.from(to)));
    }
}
