package com.zephyr.scraper.request.url.impl;

import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.protocol.enums.SearchEngine;

public class DefaultUrlProvider extends AbstractUrlProvider {

    public DefaultUrlProvider(SearchEngine engine) {
        super(engine);
    }

    @Override
    public String provideBaseUrl(QueryDto query) {
        return getScraperConfigurationService().getBaseUrl(getEngine());
    }
}
