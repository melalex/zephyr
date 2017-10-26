package com.zephyr.scraper.loader.context.strategy.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.config.ConfigurationManager;
import com.zephyr.scraper.loader.context.model.RequestContext;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;

@Component
public class DirectRequestStrategy extends AbstractRequestStrategy {
    private final Map<SearchEngine, LocalDateTime> direct = new ConcurrentHashMap<>();

    @Setter(onMethod = @__(@Autowired))
    private ConfigurationManager configurationManager;

    @Override
    protected Mono<RequestContext> configure(RequestContext context) {
        SearchEngine engine = context.getProvider();
        Duration timeout = Duration.ofMillis(configurationManager.configFor(engine).getDelay());
        LocalDateTime schedule = direct.compute(engine, (k, v) -> nonNull(v) ? v.plus(timeout) : now().plus(timeout));
        Duration duration = Duration.between(now(), schedule);

        return Mono.empty()
                .delaySubscription(duration)
                .then(Mono.just(context));
    }

    @Override
    public void report(RequestContext context) {

    }
}
