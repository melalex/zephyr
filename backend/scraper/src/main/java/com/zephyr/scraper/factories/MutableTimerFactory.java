package com.zephyr.scraper.factories;

import com.zephyr.scraper.domain.MutableTimer;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Disposables;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Scheduler;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class MutableTimerFactory {

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Setter(onMethod = @__(@Autowired))
    private Scheduler scheduler;

    public MutableTimer create(MonoSink<Void> sink, LocalDateTime dateTime) {
        return MutableTimer.builder()
                .sink(sink)
                .dateTime(new AtomicReference<>(dateTime))
                .clock(clock)
                .scheduler(scheduler)
                .swap(Disposables.swap())
                .build()
                .schedule();
    }
}
