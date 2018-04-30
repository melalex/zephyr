package com.zephyr.scraper.data;

import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public class ScraperTestProperties {

    public static final Duration DELAY = Duration.ofMillis(480000);
    public static final Duration ERROR_DELAY = Duration.ofMillis(480000);
    public static final Duration BACKOFF = Duration.ofMillis(5000);
    public static final int RETRY_COUNT = 3;
}
