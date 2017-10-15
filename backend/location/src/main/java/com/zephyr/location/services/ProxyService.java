package com.zephyr.location.services;

import com.zephyr.data.criteria.ProxyCriteria;
import com.zephyr.location.services.dto.ProxyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProxyService {

    Mono<Page<ProxyDto>> findByCountryIsoCode(ProxyCriteria criteria, Pageable pageable);

    Flux<String> findProxiesLocations();
}