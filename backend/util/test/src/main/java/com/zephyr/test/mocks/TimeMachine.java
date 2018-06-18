package com.zephyr.test.mocks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@AllArgsConstructor
public final class TimeMachine {

    private static final long CURRENT_TIME = 1510134473134L;
    private static final Instant INSTANT = Instant.ofEpochMilli(CURRENT_TIME);
    private static final ZoneId ZONE_ID = ZoneOffset.UTC;

    private Clock clock;

    public static TimeMachine create() {
        var clock = mock(Clock.class);

        when(clock.instant()).thenReturn(INSTANT);
        when(clock.getZone()).thenReturn(ZONE_ID);

        return new TimeMachine(clock);
    }

    public static LocalDateTime canonicalNow() {
        return LocalDateTime.ofInstant(INSTANT, ZONE_ID);
    }

    public Clock clock() {
        return clock;
    }

    public void reset() {
        when(clock.instant()).thenReturn(INSTANT);
    }

    public LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    public void travel(LocalDateTime time) {
        when(clock.instant()).thenReturn(time.atZone(ZONE_ID).toInstant());
    }

    public void travel(Duration duration) {
        travel(now().plus(duration));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("now", now())
                .toString();
    }
}
