package com.zephyr.proxy.repositories;

import com.mongodb.client.result.UpdateResult;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.proxy.domain.Proxy;
import reactor.core.publisher.Mono;

public interface ProxyOperations {

    Mono<Proxy> findForReservation(SearchEngine engine);

    Mono<UpdateResult> report(String id, SearchEngine engine);

    Mono<UpdateResult> resetFailedProxies();
}