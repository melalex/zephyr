package com.zephyr.proxy.repositories;

import com.zephyr.proxy.domain.Proxy;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProxyRepository extends ReactiveMongoRepository<Proxy, String>, ProxyOperations {

}