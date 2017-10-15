package com.zephyr.location.repositories;

import com.zephyr.location.domain.Proxy;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProxyRepository extends ReactiveMongoRepository<Proxy, String>, ExtendedProxyRepository {

}
