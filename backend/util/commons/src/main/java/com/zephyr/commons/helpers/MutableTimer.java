package com.zephyr.commons.helpers;

import com.zephyr.commons.TimeUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Schedulers;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MutableTimer {
    private MonoSink<Void> sink;
    private Disposable.Swap swap;
    private Clock clock;

    @Getter
    private AtomicReference<LocalDateTime> dateTime;

    public static MutableTimer create(MonoSink<Void> sink, LocalDateTime dateTime, Clock clock) {
        Disposable.Swap swap = Disposables.swap();
        MutableTimer timer = new MutableTimer();
        swap.update(timer.schedule(dateTime));

        timer.setSink(sink);
        timer.setDateTime(new AtomicReference<>(dateTime));
        timer.setSwap(swap);
        timer.setClock(clock);

        return timer;
    }

    public void reSchedule(Duration duration) {
        swap.update(schedule(dateTime.updateAndGet(d -> d.plus(duration))));
    }

    private Disposable schedule(LocalDateTime dateTime) {
        return Schedulers.single()
                .schedule(sink::success, TimeUtils.milisToDateTime(dateTime, clock), TimeUnit.MILLISECONDS);
    }
}