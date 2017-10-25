package com.zephyr.scraper.source;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.Proxy;
import reactor.core.publisher.Mono;

public interface ProxySource {

    Mono<Proxy> getOne(SearchEngine engine);

    Mono<Void> report(Proxy proxy, SearchEngine engine);

    Mono<Void> report(Proxy proxy);
}