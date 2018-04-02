package com.zephyr.scraper.request.url.impl;

import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.protocol.dto.CountryDto;
import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.data.protocol.enums.SearchEngine;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class YandexUrlProvider extends AbstractUrlProvider {

    public YandexUrlProvider() {
        super(SearchEngine.YANDEX);
    }

    @Override
    public String provideBaseUrl(QueryDto query) {
        return Optional.ofNullable(query)
                .map(QueryDto::getPlace)
                .map(PlaceDto::getCountry)
                .map(CountryDto::getLocaleYandex)
                .orElse(this.getScraperConfigurationService().getBaseUrl(getEngine()));
    }
}
