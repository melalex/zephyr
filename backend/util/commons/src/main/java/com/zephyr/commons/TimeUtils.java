package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

@UtilityClass
public class TimeUtils {

    public long millisToNow(LocalDateTime dateTime, Clock clock) {
        return Duration.between(LocalDateTime.now(clock), dateTime).toMillis();
    }
}