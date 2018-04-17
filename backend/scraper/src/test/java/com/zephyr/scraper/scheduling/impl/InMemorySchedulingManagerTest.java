package com.zephyr.scraper.scheduling.impl;

import com.zephyr.test.extensions.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.time.Duration;

@ExtendWith(MockitoExtension.class)
@Tags({@Tag("scheduling"), @Tag("unit")})
class InMemorySchedulingManagerTest {

    private static final String FIRST_GROUP = "FIRST_GROUP";
    private static final String SECOND_GROUP = "SECOND_GROUP";

    private static final Duration RESCHEDULE_DURATION = Duration.ofDays(1);
    private static final Duration DURATION = Duration.ofDays(2);

    private InMemorySchedulingManager testInstance;

    @BeforeEach
    void setUp() {
        Scheduler scheduler = Schedulers.elastic();
        Clock clock = Clock.systemDefaultZone();

        testInstance = new InMemorySchedulingManager(scheduler, clock);
    }

    @Test
    void shouldScheduleNext() {
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
    void shouldReSchedule() {
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