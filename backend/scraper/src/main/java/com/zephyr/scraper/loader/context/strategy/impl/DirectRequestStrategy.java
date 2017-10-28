package com.zephyr.scraper.loader.context.strategy.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.loader.context.model.RequestContext;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DirectRequestStrategy extends AbstractRequestStrategy {
    private static final String ERROR_MESSAGE = "report method should be called after toContext";

    private final Map<SearchEngine, LocalDateTime> direct = new ConcurrentHashMap<>();

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @Override
    protected Mono<RequestContext> configure(RequestContext context) {
        SearchEngine engine = context.getProvider();
        Duration timeout = Duration.ofMillis(properties.getScraper(engine).getDelay());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime schedule = direct.compute(engine, (k, v) -> reserve(now, v, timeout));
        Duration duration = Duration.between(now, schedule);

        return Mono.empty()
                .delaySubscription(duration)
                .then(Mono.just(context));
    }

    @Override
    public void report(RequestContext context) {
        SearchEngine engine = context.getProvider();
        Duration timeout = Duration.ofMillis(properties.getScraper(engine).getErrorDelay());

        direct.compute(engine, (k, v) -> relax(v, timeout));
    }

    private LocalDateTime reserve(LocalDateTime now, LocalDateTime previous, Duration duration) {
        if (previous == null || previous.isBefore(now)) {
            return now;
        }

        return previous.plus(duration);
    }

    private LocalDateTime relax(LocalDateTime previous, Duration duration) {
        return Optional.ofNullable(previous)
                .orElseThrow(() -> new IllegalStateException(ERROR_MESSAGE))
                .plus(duration);
    }
}