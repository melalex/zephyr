package com.zephyr.scraper.request.url.impl;

import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.protocol.dto.CountryDto;
import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.data.protocol.enums.SearchEngine;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GoogleUrlProvider extends AbstractUrlProvider {

    public GoogleUrlProvider() {
        super(SearchEngine.GOOGLE);
    }

    @Override
    public String provideBaseUrl(QueryDto query) {
        return Optional.ofNullable(query)
                .map(QueryDto::getPlace)
                .map(PlaceDto::getCountry)
                .map(CountryDto::getLocaleGoogle)
                .orElse(getScraperConfigurationService().getBaseUrl(getEngine()));
    }
}
