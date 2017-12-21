package com.zephyr.scraper.browser;

import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
import reactor.core.publisher.Mono;

public interface Browser {

    Mono<EngineResponse> get(EngineRequest engineRequest);

    void report(EngineResponse response);
}