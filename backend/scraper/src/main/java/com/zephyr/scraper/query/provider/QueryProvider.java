package com.zephyr.scraper.query.provider;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Task;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface QueryProvider {

    Mono<Request> provide(Task task);
}
