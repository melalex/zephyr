package com.zephyr.scraper.scheduler.strategy;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.RequestContext;
import reactor.core.publisher.Mono;

public interface RequestStrategy {

    Mono<RequestContext> configureAndBuild(SearchEngine engine, RequestContext.RequestContextBuilder builder);

    void report(RequestContext context);
}
