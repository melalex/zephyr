package com.zephyr.location.repositories;

import reactor.core.publisher.Flux;

public interface ExtendedProxyRepository {

    Flux<String> findProxiesLocations();
}
