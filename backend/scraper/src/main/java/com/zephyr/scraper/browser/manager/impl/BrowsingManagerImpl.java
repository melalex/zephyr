package com.zephyr.scraper.browser.manager.impl;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.browser.manager.BrowsingManager;
import com.zephyr.scraper.browser.provider.BrowsingProvider;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrowsingManagerImpl implements BrowsingManager {
    private static final String UNSUPPORTED_REQUEST_TYPE = "Browser doesn't support RequestType '%s' of '%s' engine";

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
    public BrowsingProvider manage(SearchEngine engine) {
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
                throw new IllegalArgumentException(String.format(UNSUPPORTED_REQUEST_TYPE, requestType, engine));
        }
    }

}
