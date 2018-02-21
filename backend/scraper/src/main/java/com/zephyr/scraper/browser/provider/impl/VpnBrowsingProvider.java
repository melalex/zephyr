package com.zephyr.scraper.browser.provider.impl;

import com.zephyr.scraper.configuration.properties.ScraperProperties;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class VpnBrowsingProvider extends AbstractBrowsingProvider {
    private static final String ERROR_MESSAGE = "No implementation";

    public VpnBrowsingProvider() {
        super(ScraperProperties.RequestType.VPN);
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
