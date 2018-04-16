package com.zephyr.scraper.scheduling.impl;

import com.zephyr.commons.TimeUtils;
import com.zephyr.scraper.scheduling.SchedulingManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
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
@AllArgsConstructor
public class InMemorySchedulingManager implements SchedulingManager {

    private static final String NEW_TASK_MESSAGE = "Schedule new task for group [{}] on [{}]";
    private static final String RESCHEDULE_MESSAGE = "Reschedule all tasks in group [{}] with [{}] duration";

    private final Map<String, Deque<MutableTimer>> state = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> lastAdded = new ConcurrentHashMap<>();

    @NonNull
    private Scheduler scheduler;

    @NonNull
    private Clock clock;

    @Override
    public Publisher<Void> scheduleNext(Enum<?> group, Duration duration) {
        return scheduleNext(group.name(), duration);
    }

    @Override
    public Publisher<Void> scheduleNext(String groupName, Duration duration) {
        Deque<MutableTimer> group = group(groupName);
        LocalDateTime dateTime = pushLast(groupName, duration);

        log.info(NEW_TASK_MESSAGE, groupName, dateTime);

        return Mono.<Void>create(s -> group.addLast(timer(s, dateTime)))
                .doOnSuccess(ignore -> group.poll());
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

        log.info(RESCHEDULE_MESSAGE, groupName, duration);
    }

    private MutableTimer timer(MonoSink<Void> sink, LocalDateTime dateTime) {
        return MutableTimer.builder()
                .sink(sink)
                .dateTime(new AtomicReference<>(dateTime))
                .clock(clock)
                .scheduler(scheduler)
                .swap(Disposables.swap())
                .build()
                .schedule();
    }

    private LocalDateTime pushLast(String group, Duration duration) {
        LocalDateTime now = LocalDateTime.now(clock);
        return lastAdded.compute(group, (k, v) -> Objects.isNull(v) || v.isBefore(now) ? now : v.plus(duration));
    }

    private Deque<MutableTimer> group(String groupName) {
        return state.compute(groupName, (g, d) -> Optional.ofNullable(d).orElse(new ConcurrentLinkedDeque<>()));
    }

    @Value
    @Builder
    private static class MutableTimer {

        private MonoSink<Void> sink;
        private AtomicReference<LocalDateTime> dateTime;
        private Scheduler scheduler;
        private Disposable.Swap swap;
        private Clock clock;

        private void reSchedule(Duration duration) {
            swap.update(schedule(dateTime.updateAndGet(d -> d.plus(duration))));
        }

        private MutableTimer schedule() {
            swap.update(schedule(dateTime.get()));
            return this;
        }

        private Disposable schedule(LocalDateTime dateTime) {
            return scheduler.schedule(sink::success, TimeUtils.millisToNow(dateTime, clock), TimeUnit.MILLISECONDS);
        }
    }
}
