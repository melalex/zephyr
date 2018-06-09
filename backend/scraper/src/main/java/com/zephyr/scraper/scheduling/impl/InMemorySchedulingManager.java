package com.zephyr.scraper.scheduling.impl;

import com.zephyr.commons.TimeUtils;
import com.zephyr.scraper.scheduling.SchedulingManager;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Scheduler;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class InMemorySchedulingManager implements SchedulingManager {

    private final Map<String, Deque<MutableTimer>> state = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> lastAdded = new ConcurrentHashMap<>();

    @Getter(AccessLevel.PRIVATE)
    @Setter(onMethod = @__(@Autowired))
    private Scheduler scheduler;

    @Getter(AccessLevel.PRIVATE)
    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Override
    public Publisher<Void> scheduleNext(Enum<?> group, Duration duration) {
        return scheduleNext(group.name(), duration);
    }

    @Override
    public Publisher<Void> scheduleNext(String groupName, Duration duration) {
        Deque<MutableTimer> group = group(groupName);
        LocalDateTime dateTime = pushLast(groupName, duration);

        log.info("Schedule new task for group [ {} ] on [ {} ]", groupName, dateTime);

        return Mono.<Void>create(s -> group.addLast(timer(s, dateTime)))
                .doOnSuccess(ignore -> onTrigger(group));
    }

    @Override
    public void reSchedule(Enum<?> group, Duration duration) {
        reSchedule(group.name(), duration);
    }

    @Override
    public void reSchedule(String groupName, Duration duration) {
        Deque<MutableTimer> group = group(groupName);
        pushLast(groupName, duration);
        group.forEach(t -> t.reSchedule(duration));

        log.info("Reschedule all tasks in group [ {} ] with [ {} ] duration", groupName, duration);
    }

    private void onTrigger(Deque<MutableTimer> group) {
        if (group.size() > 1) {
            group.poll();
        }

        if (log.isDebugEnabled()) {
            log.debug("Trigger new task at [ {} ]", LocalDateTime.now(clock));
        }
    }

    private MutableTimer timer(MonoSink<Void> sink, LocalDateTime dateTime) {
        return MutableTimer.builder()
                .sink(sink)
                .dateTime(new AtomicReference<>(dateTime))
                .swap(Disposables.swap())
                .parent(this)
                .build()
                .schedule();
    }

    private LocalDateTime pushLast(String group, Duration duration) {
        return lastAdded.compute(group, (k, v) -> nextTriggerTime(duration, v));
    }

    private LocalDateTime nextTriggerTime(Duration duration, LocalDateTime previous) {
        LocalDateTime now = LocalDateTime.now(clock);

        return Objects.isNull(previous) || previous.plus(duration).isBefore(now)
               ? now
               : previous.plus(duration);
    }

    private Deque<MutableTimer> group(String groupName) {
        return state.compute(groupName, (g, d) -> Optional.ofNullable(d).orElse(new ConcurrentLinkedDeque<>()));
    }

    @Value
    @Builder
    private static class MutableTimer {

        private InMemorySchedulingManager parent;
        private MonoSink<Void> sink;
        private AtomicReference<LocalDateTime> dateTime;
        private Disposable.Swap swap;

        private void reSchedule(Duration duration) {
            swap.update(schedule(dateTime.updateAndGet(d -> d.plus(duration))));
        }

        private MutableTimer schedule() {
            swap.update(schedule(dateTime.get()));
            return this;
        }

        private Disposable schedule(LocalDateTime dateTime) {
            long delay = TimeUtils.millisFromNow(dateTime, parent.getClock());

            return parent.getScheduler().schedule(sink::success, delay, TimeUnit.MILLISECONDS);
        }
    }
}
