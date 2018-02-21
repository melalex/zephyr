package com.zephyr.scraper.browser.provider.impl;

import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.properties.ScraperProperties;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProxiedBrowsingProvider extends AbstractBrowsingProvider {
    private static final String ERROR_MESSAGE = "No implementation";

    public ProxiedBrowsingProvider() {
        super(ScraperProperties.RequestType.PROXY);
    }

    @Override
    public Mono<EngineResponse> get(EngineRequest engineRequest) {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public void report(EngineResponse response) {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }
}
