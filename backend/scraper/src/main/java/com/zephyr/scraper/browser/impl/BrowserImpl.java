package com.zephyr.scraper.browser.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.browser.Browser;
import com.zephyr.scraper.browser.provider.BrowsingProvider;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BrowserImpl implements Browser {

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private BrowsingProvider directBrowsingProvider;

    @Setter(onMethod = @__(@Autowired))
    private BrowsingProvider proxiedBrowsingProvider;

    @Setter(onMethod = @__(@Autowired))
    private BrowsingProvider vpnBrowsingProvider;

    @Setter(onMethod = @__(@Autowired))
    private BrowsingProvider torBrowsingProvider;

    @Override
    public Mono<EngineResponse> get(EngineRequest engineRequest) {
        return manage(engineRequest.getProvider()).get(engineRequest);
    }

    @Override
    public void report(EngineResponse response) {
        manage(response.getProvider()).report(response);
    }

    private BrowsingProvider manage(SearchEngine engine) {
        ScraperProperties.RequestType requestType = scraperProperties.getScraper(engine).getRequestType();
        switch (requestType) {
            case DIRECT:
                return directBrowsingProvider;
            case PROXY:
                return proxiedBrowsingProvider;
            case VPN:
                return vpnBrowsingProvider;
            case TOR:
                return torBrowsingProvider;
            default:
                String message = String
                        .format("Browser doesn't support RequestType '%s' of '%s' engine", requestType, engine);
                throw new IllegalArgumentException(message);
        }
    }
}
