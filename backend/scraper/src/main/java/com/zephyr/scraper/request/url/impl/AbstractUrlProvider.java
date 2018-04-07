package com.zephyr.scraper.request.url.impl;

import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.request.url.UrlProvider;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@RequiredArgsConstructor
abstract class AbstractUrlProvider implements UrlProvider {

    @NonNull
    private SearchEngine engine;

    @Setter(onMethod = @__(@Autowired))
    private ScraperConfigurationService scraperConfigurationService;

    @Override
    public String provideUri(QueryDto query) {
        return getScraperConfigurationService().getPath(getEngine());
    }
}
