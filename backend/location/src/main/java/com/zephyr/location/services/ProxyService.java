package com.zephyr.location.services;

import com.zephyr.data.ProxyDto;
import reactor.core.publisher.Flux;

public interface ProxyService {

    Flux<ProxyDto> findByCountryIsoCode(String iso);

    Flux<String> findProxiesLocations();
}