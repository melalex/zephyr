package com.zephyr.scraper.scheduling.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Publisher;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.time.Duration;

@RunWith(MockitoJUnitRunner.class)
public class InMemorySchedulingManagerTest {

    private static final String FIRST_GROUP = "FIRST_GROUP";
    private static final String SECOND_GROUP = "SECOND_GROUP";

    private static final Duration RESCHEDULE_DURATION = Duration.ofDays(1);
    private static final Duration DURATION = Duration.ofDays(2);

    private InMemorySchedulingManager testInstance;

    @Before
    public void setUp() {
        Scheduler scheduler = Schedulers.elastic();
        Clock clock = Clock.systemDefaultZone();

        testInstance = new InMemorySchedulingManager(scheduler, clock);
    }

    @Test
    public void shouldScheduleNext() {
        StepVerifier.create(testInstance.scheduleNext(FIRST_GROUP, DURATION))
                .expectNoEvent(DURATION)
                .verifyComplete();

        StepVerifier.create(testInstance.scheduleNext(FIRST_GROUP, DURATION))
                .expectNoEvent(DURATION.plus(DURATION))
                .verifyComplete();

        StepVerifier.create(testInstance.scheduleNext(SECOND_GROUP, DURATION))
                .expectNoEvent(DURATION)
                .verifyComplete();
    }

    @Test
    public void shouldReSchedule() {
        Publisher<Void> first = testInstance.scheduleNext(FIRST_GROUP, DURATION);
        Publisher<Void> second = testInstance.scheduleNext(FIRST_GROUP, DURATION);
        Publisher<Void> third = testInstance.scheduleNext(SECOND_GROUP, DURATION);

        testInstance.reSchedule(FIRST_GROUP, RESCHEDULE_DURATION);

        StepVerifier.create(first)
                .expectNoEvent(DURATION.plus(RESCHEDULE_DURATION))
                .verifyComplete();

        StepVerifier.create(second)
                .expectNoEvent(DURATION.plus(DURATION).plus(RESCHEDULE_DURATION))
                .verifyComplete();

        StepVerifier.create(third)
                .expectNoEvent(DURATION)
                .verifyComplete();
    }
}