package com.zephyr.scraper.request.url.impl;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.domain.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GoogleUrlProvider extends AbstractUrlProvider {

    public GoogleUrlProvider() {
        super(SearchEngine.GOOGLE);
    }

    @Override
    public String provideBaseUrl(Query query) {
        return Optional.ofNullable(query)
                .map(Query::getPlace)
                .map(Query.Place::getCountry)
                .map(Query.Country::getLocaleGoogle)
                .orElse(getScraperConfigurationService().getBaseUrl(getEngine()));
    }
}
