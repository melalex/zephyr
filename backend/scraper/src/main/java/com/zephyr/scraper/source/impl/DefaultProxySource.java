package com.zephyr.scraper.source.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.source.ProxySource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DefaultProxySource implements ProxySource {
    private static final String ERROR_MESSAGE = "No implementation";

    @Override
    public Mono<Proxy> reserve(SearchEngine engine) {
        throw new UnsupportedOperationException("No implementation");
    }

    @Override
    public Mono<Void> report(String id, SearchEngine engine) {
        throw new UnsupportedOperationException("No implementation");
    }
}
