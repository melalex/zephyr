package com.zephyr.scraper.browser.provider.internal.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.browser.provider.internal.Scheduler;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SchedulerImpl implements Scheduler {
    private final Map<SearchEngine, LocalDateTime> state = new ConcurrentHashMap<>();

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Override
    public Duration reserve(SearchEngine engine) {
        Duration timeout = Duration.ofMillis(properties.getScraper(engine).getDelay());
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime schedule = state.compute(engine, (k, v) -> reserve(now, v, timeout));

        return Duration.between(now, schedule);
    }

    @Override
    public void report(SearchEngine engine) {
        Duration timeout = Duration.ofMillis(properties.getScraper(engine).getErrorDelay());

        state.compute(engine, (k, v) -> relax(v, timeout));
    }

    private LocalDateTime reserve(LocalDateTime now, LocalDateTime previous, Duration duration) {
        return previous == null || previous.isBefore(now) ? now : previous.plus(duration);
    }

    private LocalDateTime relax(LocalDateTime previous, Duration duration) {
        return Optional.ofNullable(previous)
                .orElseThrow(() -> new IllegalStateException("report method should be called after reserve"))
                .plus(duration);
    }
}
