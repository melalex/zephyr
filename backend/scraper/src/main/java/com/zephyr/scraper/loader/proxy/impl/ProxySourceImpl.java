package com.zephyr.scraper.loader.proxy.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.Proxy;
import com.zephyr.scraper.loader.proxy.ProxySource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProxySourceImpl implements ProxySource {

    @Override
    public Mono<Proxy> getOne(SearchEngine engine) {
        return null;
    }

    @Override
    public Mono<Void> report(Proxy proxy, SearchEngine engine) {
        return null;
    }
}
