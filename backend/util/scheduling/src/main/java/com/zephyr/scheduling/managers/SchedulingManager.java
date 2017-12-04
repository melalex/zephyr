package com.zephyr.scheduling.managers;

import org.reactivestreams.Publisher;

import java.time.Duration;

public interface SchedulingManager {

    Publisher<Void> scheduleNext(Enum<?> group, Duration duration);

    Publisher<Void> scheduleNext(String group, Duration duration);

    Publisher<Void> reSchedule(Enum<?> group, Duration duration);

    Publisher<Void> reSchedule(String group, Duration duration);
}
