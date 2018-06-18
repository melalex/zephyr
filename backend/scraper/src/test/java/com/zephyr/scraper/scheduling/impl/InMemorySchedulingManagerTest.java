package com.zephyr.scraper.scheduling.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.scheduler.clock.SchedulerClock;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Clock;
import java.time.Duration;

@RunWith(MockitoJUnitRunner.class)
public class InMemorySchedulingManagerTest {

    private static final String FIRST_GROUP = "FIRST_GROUP";
    private static final String SECOND_GROUP = "SECOND_GROUP";

    private static final Duration RESCHEDULE_DURATION = Duration.ofDays(1);
    private static final Duration DURATION = Duration.ofDays(2);

    private InMemorySchedulingManager testInstance;
    private Clock clock;

    @Before
    public void setUp() {
        var scheduler = VirtualTimeScheduler.getOrSet();

        clock = SchedulerClock.of(scheduler);
        testInstance = new InMemorySchedulingManager();
        testInstance.setScheduler(scheduler);
        testInstance.setClock(clock);
    }

    @Test
    public void shouldScheduleNext() {
        StepVerifier.create(testInstance.scheduleNext(FIRST_GROUP, DURATION))
                .verifyComplete();

        resetInstance();

        StepVerifier.withVirtualTime(() -> testInstance.scheduleNext(FIRST_GROUP, DURATION))
                .expectSubscription()
                .expectNoEvent(DURATION)
                .verifyComplete();

        resetInstance();

        StepVerifier.create(testInstance.scheduleNext(SECOND_GROUP, DURATION))
                .verifyComplete();
    }

    @Test
    public void shouldReSchedule() {
        StepVerifier.withVirtualTime(() -> testInstance.scheduleNext(FIRST_GROUP, DURATION))
                .verifyComplete();

        resetInstance();

        testInstance.reSchedule(FIRST_GROUP, RESCHEDULE_DURATION);

        StepVerifier.withVirtualTime(() -> testInstance.scheduleNext(FIRST_GROUP, DURATION))
                .expectSubscription()
                .expectNoEvent(DURATION.plus(RESCHEDULE_DURATION))
                .verifyComplete();
    }

    private void resetInstance() {
        var scheduler = VirtualTimeScheduler.getOrSet();
        scheduler.advanceTimeTo(clock.instant());

        clock = SchedulerClock.of(scheduler);
        testInstance.setScheduler(scheduler);
        testInstance.setClock(clock);
    }
}