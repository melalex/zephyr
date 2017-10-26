package com.zephyr.proxy.repositories;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.proxy.domain.Proxy;
import reactor.core.publisher.Mono;

public interface ProxyOperations {

    Mono<Proxy> reserve(SearchEngine engine);
}