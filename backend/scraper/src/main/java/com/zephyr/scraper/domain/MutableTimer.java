package com.zephyr.scraper.domain;

import com.zephyr.commons.TimeUtils;
import lombok.Builder;
import lombok.Value;
import reactor.core.Disposable;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Scheduler;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Value
@Builder
public class MutableTimer {
    private MonoSink<Void> sink;
    private AtomicReference<LocalDateTime> dateTime;
    private Scheduler scheduler;
    private Disposable.Swap swap;
    private Clock clock;

    public void reSchedule(Duration duration) {
        swap.update(schedule(dateTime.updateAndGet(d -> d.plus(duration))));
    }

    public MutableTimer schedule() {
        swap.update(schedule(dateTime.get()));
        return this;
    }

    private Disposable schedule(LocalDateTime dateTime) {
        return scheduler.schedule(sink::success, TimeUtils.millisToNow(dateTime, clock), TimeUnit.MILLISECONDS);
    }
}