package com.zephyr.scraper.scheduling.impl;

import com.zephyr.scraper.domain.MutableTimer;
import com.zephyr.scraper.factories.MutableTimerFactory;
import com.zephyr.scraper.scheduling.SchedulingManager;
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
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
@Component
public class InMemorySchedulingManager implements SchedulingManager {
    private static final String NEW_TASK_MESSAGE = "Schedule new task for group {} on {}";
    private static final String RESCHEDULE_MESSAGE = "Reschedule all tasks in group with {} duration";

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
        log.info(NEW_TASK_MESSAGE, group, dateTime);

        return Mono.<Void>create(s -> group.addLast(timerFactory.create(s, dateTime)))
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

    private LocalDateTime pushLast(String group, Duration duration) {
        LocalDateTime now = LocalDateTime.now(clock);
        return lastAdded.compute(group, (k, v) -> Objects.isNull(v) || v.isBefore(now) ? now : v.plus(duration));
    }

    private Deque<MutableTimer> group(String groupName) {
        return state.compute(groupName, (g, d) -> Optional.ofNullable(d).orElse(new ConcurrentLinkedDeque<>()));
    }
}
