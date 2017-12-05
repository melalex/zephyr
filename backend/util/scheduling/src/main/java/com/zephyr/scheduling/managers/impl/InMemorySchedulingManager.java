package com.zephyr.scheduling.managers.impl;

import com.zephyr.scheduling.domain.MutableTimer;
import com.zephyr.scheduling.factories.MutableTimerFactory;
import com.zephyr.scheduling.managers.SchedulingManager;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
@Component
public class InMemorySchedulingManager implements SchedulingManager {
    private final Map<String, Deque<MutableTimer>> state = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> lastAdded = new ConcurrentHashMap<>();

    @Setter(onMethod = @__(@Autowired))
    private MutableTimerFactory timerFactory;

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
        log.info("Schedule new task for group {} on {}", group, dateTime);

        return Mono.<Void>create(s -> group.addLast(timerFactory.create(s, dateTime)))
                .doOnSuccess(ignore -> group.poll());
    }

    @Override
    public void onError(Enum<?> group, Duration duration) {
        onError(group.name(), duration);
    }

    @Override
    public void onError(String groupName, Duration duration) {
        Deque<MutableTimer> group = group(groupName);
        pushLast(groupName, duration);
        group.forEach(t -> t.reSchedule(duration));

        log.info("Reschedule all tasks in group with {} duration", groupName, duration);
    }

    private LocalDateTime pushLast(String group, Duration duration) {
        LocalDateTime now = LocalDateTime.now(clock);
        return lastAdded.compute(group, (k, v) -> v == null || v.isBefore(now) ? now : v.plus(duration));
    }

    private Deque<MutableTimer> group(String groupName) {
        return state.compute(groupName, (g, d) -> Objects.isNull(d) ? new ConcurrentLinkedDeque<>() : d);
    }
}
