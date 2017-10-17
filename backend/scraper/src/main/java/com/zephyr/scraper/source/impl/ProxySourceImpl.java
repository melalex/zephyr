package com.zephyr.scraper.source.impl;

import com.zephyr.scraper.domain.Proxy;
import com.zephyr.scraper.source.ProxySource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProxySourceImpl implements ProxySource {

    @Override
    public Mono<Proxy> getByIso(String iso) {
        return null;
    }

    @Override
    public Mono<Proxy> exchange(Proxy proxy) {
        return null;
    }
}
