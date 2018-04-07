package com.zephyr.scraper.request.url.impl;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.domain.Query;

public class DefaultUrlProvider extends AbstractUrlProvider {

    public DefaultUrlProvider(SearchEngine engine) {
        super(engine);
    }

    @Override
    public String provideBaseUrl(Query query) {
        return getScraperConfigurationService().getBaseUrl(getEngine());
    }
}
