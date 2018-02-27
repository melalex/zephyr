package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import java.time.*;
import java.util.Date;

@UtilityClass
public class TimeUtils {

    public long millisToNow(LocalDateTime dateTime, Clock clock) {
        return Duration.between(LocalDateTime.now(clock), dateTime).toMillis();
    }

    public Date toDate(LocalDate localDate) {
        return Date.from(LocalDateTime.from(localDate).toInstant(ZoneOffset.UTC));
    }

    public LocalDate toLocalDate(Date date) {
        return LocalDate.from(date.toInstant());
    }
}