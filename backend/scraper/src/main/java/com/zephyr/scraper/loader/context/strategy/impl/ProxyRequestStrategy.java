package com.zephyr.scraper.loader.context.strategy.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.clients.ProxyServiceClient;
import com.zephyr.scraper.loader.context.model.RequestContext;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static java.time.LocalDateTime.now;

@Slf4j
@Component
@RefreshScope
public class ProxyRequestStrategy extends AbstractRequestStrategy {

    @Setter(onMethod = @__(@Autowired))
    private ProxyServiceClient proxyServiceClient;

    @Override
    protected Mono<RequestContext> configure(RequestContext context) {
        int page = context.getPage().getNumber();
        String task = context.getTask().getId();
        SearchEngine engine = context.getProvider();

        return proxyServiceClient.reserve(context.getProvider())
                .doOnNext(p -> log.info("Received proxy: {}", p))
                .doOnNext(p -> log.info("Schedule request via Proxy {} on {} for TaskDto {}, {} page and Engine {}",
                        p.getId(), p.getSchedule(), task, page, engine))
                .doOnNext(context::setProxy)
                .flatMap(p -> Mono.just(context).delaySubscription(Duration.between(now(), p.getSchedule())));
    }

    @Override
    public void report(RequestContext context) {
        int page = context.getPage().getNumber();
        String task = context.getTask().getId();
        String proxy = context.getProxy().getId();
        SearchEngine engine = context.getProvider();

        proxyServiceClient.report(context.getProxy().getId(), context.getProvider())
                .doOnNext(v -> log.info("Proxy request error handled for Proxy {}, TaskDto {} and Engine {} on {} page",
                        proxy, task, engine, page))
                .subscribe();
    }
}