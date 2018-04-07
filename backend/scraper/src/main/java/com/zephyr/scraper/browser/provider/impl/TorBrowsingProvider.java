package com.zephyr.scraper.browser.provider.impl;

import com.zephyr.scraper.browser.provider.BrowsingProvider;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TorBrowsingProvider implements BrowsingProvider {

    private static final String ERROR_MESSAGE = "No implementation";

    @Override
    public Mono<EngineResponse> get(EngineRequest engineRequest) {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public void onFail(EngineResponse response) {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }
}