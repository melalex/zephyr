package com.zephyr.scraper.loader.context.strategy.impl;

import com.zephyr.scraper.loader.context.model.RequestContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProxyRequestStrategy extends AbstractRequestStrategy {

    @Override
    protected Mono<RequestContext> configure(RequestContext context) {
        return null;
    }

    @Override
    public void report(RequestContext context) {

    }
}
