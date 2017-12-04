package com.zephyr.scheduling.managers.impl;

import com.zephyr.scheduling.domain.MutableTimer;
import com.zephyr.scheduling.factories.MutableTimerFactory;
import com.zephyr.scheduling.managers.SchedulingManager;
import lombok.Setter;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemorySchedulingManager implements SchedulingManager {
    private final Map<String, Deque<MutableTimer>> state = new ConcurrentHashMap<>();

    @Setter(onMethod = @__(@Autowired))
    private MutableTimerFactory timerFactory;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Override
    public Publisher<Void> scheduleNext(Enum<?> group, Duration duration) {
        return scheduleNext(group.name(), duration);
    }

    @Override
    public Publisher<Void> scheduleNext(String group, Duration duration) {
        Deque<MutableTimer> deque = deque(group);
        return Mono.<Void>create(s -> schedule(deque, s, duration))
                .doOnSuccess(ignore -> deque.poll());
    }

    @Override
    public Publisher<Void> reSchedule(Enum<?> group, Duration duration) {
        return reSchedule(group.name(), duration);
    }

    @Override
    public Publisher<Void> reSchedule(String group, Duration duration) {
        Deque<MutableTimer> deque = deque(group);
        deque.forEach(t -> t.reSchedule(duration));
        return Mono.<Void>create(s -> reScheduleFirst(deque, s, duration))
                .doOnSuccess(ignore -> deque.poll());
    }

    private Deque<MutableTimer> deque(String group) {
        return state.compute(group, (g, d) -> Objects.isNull(d) ? new LinkedList<>() : d);
    }

    private synchronized void schedule(Deque<MutableTimer> group, MonoSink<Void> sink, Duration duration) {
        group.addLast(timerFactory.create(sink, group.getLast().getDateTime().plus(duration)));
    }

    private synchronized void reScheduleFirst(Deque<MutableTimer> group, MonoSink<Void> sink, Duration duration) {
        group.addFirst(timerFactory.create(sink, LocalDateTime.now(clock).plus(duration)));
    }
}
