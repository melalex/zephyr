package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@UtilityClass
public class TimeUtils {

    public long millisToNow(LocalDateTime dateTime, Clock clock) {
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