package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@UtilityClass
public class TimeUtils {

    public long millisFromNow(LocalDateTime dateTime, Clock clock) {
        return Duration.between(LocalDateTime.now(clock), dateTime).toMillis();
    }

    public Date toDate(TemporalAccessor temporal) {
        return Date.from(LocalDateTime.from(temporal).toInstant(ZoneOffset.UTC));
    }

    public LocalDate toLocalDate(Date date) {
        return LocalDate.from(date.toInstant());
    }

    public LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.from(date.toInstant());
    }
}