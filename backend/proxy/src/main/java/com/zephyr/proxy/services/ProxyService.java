package com.zephyr.proxy.services;

import com.zephyr.data.dto.ProxyDto;
import com.zephyr.data.enums.SearchEngine;
import reactor.core.publisher.Mono;

public interface ProxyService {

    Mono<ProxyDto> reserve(SearchEngine engine);

    Mono<Void> report(String id, SearchEngine engine);
}
