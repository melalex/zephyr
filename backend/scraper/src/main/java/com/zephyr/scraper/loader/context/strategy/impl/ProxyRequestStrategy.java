package com.zephyr.scraper.loader.context.strategy.impl;

import com.zephyr.scraper.clients.ProxyServiceClient;
import com.zephyr.scraper.loader.context.model.RequestContext;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static java.time.LocalDateTime.now;

@Component
public class ProxyRequestStrategy extends AbstractRequestStrategy {

    @Setter(onMethod = @__(@Autowired))
    private ProxyServiceClient proxyServiceClient;

    @Override
    protected Mono<RequestContext> configure(RequestContext context) {
        return proxyServiceClient.reserve(context.getProvider())
                .doOnNext(context::setProxy)
                .flatMap(p -> Mono.just(context).delaySubscription(Duration.between(now(), p.getSchedule())));
    }

    @Override
    public void report(RequestContext context) {
        proxyServiceClient.report(context.getProxy().getId(), context.getProvider())
                .subscribe();
    }
}