package com.zephyr.scheduling.domain;

import com.zephyr.commons.TimeUtils;
import lombok.Data;
import reactor.core.Disposable;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Scheduler;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Data
public final class MutableTimer {
    private MonoSink<Void> sink;
    private AtomicReference<LocalDateTime> dateTime;
    private Scheduler scheduler;
    private Disposable.Swap swap;
    private Clock clock;

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = new AtomicReference<>(dateTime);
    }

    public void reSchedule(Duration duration) {
        swap.update(schedule(dateTime.updateAndGet(d -> d.plus(duration))));
    }

    public void schedule() {
        swap.update(schedule(dateTime.get()));
    }

    private Disposable schedule(LocalDateTime dateTime) {
        return scheduler.schedule(sink::success, TimeUtils.milisToNow(dateTime, clock), TimeUnit.MILLISECONDS);
    }
}