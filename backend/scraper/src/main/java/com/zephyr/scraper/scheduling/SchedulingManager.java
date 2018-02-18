package com.zephyr.scraper.scheduling;

import org.reactivestreams.Publisher;

import java.time.Duration;

public interface SchedulingManager {

    Publisher<Void> scheduleNext(Enum<?> group, Duration duration);

    Publisher<Void> scheduleNext(String group, Duration duration);

    void reSchedule(Enum<?> group, Duration duration);

    void reSchedule(String group, Duration duration);
}
