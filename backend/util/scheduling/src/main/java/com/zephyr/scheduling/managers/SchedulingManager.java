package com.zephyr.scheduling.managers;

import org.reactivestreams.Publisher;

import java.time.Duration;

public interface SchedulingManager {

    Publisher<Void> scheduleNext(Enum<?> group, Duration duration);

    Publisher<Void> scheduleNext(String group, Duration duration);

    void onError(Enum<?> group, Duration duration);

    void onError(String group, Duration duration);
}
