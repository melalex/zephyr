package com.zephyr.jobs.extensions;

import com.github.javafaker.Faker;
import com.zephyr.commons.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@Component
public final class ExtendedFaker {

    @Delegate
    private Faker faker;
    private TimeFaker timeFaker;

    public ExtendedFaker(Faker faker) {
        this.faker = faker;
        this.timeFaker = new TimeFaker(faker);
    }

    public TimeFaker time() {
        return timeFaker;
    }

    @AllArgsConstructor
    public static final class TimeFaker {
        private Faker faker;

        public LocalDateTime dateTime(TemporalAccessor from, TemporalAccessor to) {
            Date between = faker.date().between(TimeUtils.toDate(from), TimeUtils.toDate(to));
            return TimeUtils.toLocalDateTime(between);
        }

        public LocalDate date(TemporalAccessor from, TemporalAccessor to) {
            return LocalDate.from(dateTime(from, to));
        }
    }
}
