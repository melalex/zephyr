package com.zephyr.scraper.source;

import com.zephyr.scraper.domain.Proxy;
import reactor.core.publisher.Mono;

public interface ProxySource {

    Mono<Proxy> getOne();

    Mono<Proxy> exchange(Proxy proxy);
}