package com.zephyr.test.mocks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ClockMock {

    private static final long CURRENT_TIME = 1510134473134L;
    private static final Instant INSTANT = Instant.ofEpochMilli(CURRENT_TIME);
    private static final ZoneId ZONE_ID = ZoneOffset.UTC;

    public static Clock of() {
        Clock mock = mock(Clock.class);

        when(mock.instant()).thenReturn(INSTANT);
        when(mock.getZone()).thenReturn(ZONE_ID);

        return mock;
    }

    public static LocalDateTime now() {
        return LocalDateTime.ofInstant(INSTANT, ZONE_ID);
    }
}
