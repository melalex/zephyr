package com.zephyr.location.repositories;

import com.zephyr.location.domain.Proxy;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProxyRepository extends ReactiveMongoRepository<Proxy, String>, ExtendedProxyRepository {

    Flux<Proxy> findAllByCountryIso(String countryIso);
}
