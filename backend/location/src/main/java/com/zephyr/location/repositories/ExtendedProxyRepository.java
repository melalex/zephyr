package com.zephyr.location.repositories;

import com.zephyr.data.criteria.ProxyCriteria;
import com.zephyr.location.domain.Proxy;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExtendedProxyRepository {

    Flux<String> findProxiesLocations();

    Flux<Proxy> findByCriteria(ProxyCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(ProxyCriteria criteria);
}
