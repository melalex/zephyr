package com.zephyr.proxy.repositories;

import com.zephyr.proxy.domain.Proxy;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProxyRepository extends ReactiveMongoRepository<Proxy, String>, ProxyOperations {

}