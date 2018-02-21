package com.zephyr.scraper.browser.provider.impl;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.browser.provider.BrowsingProvider;
import com.zephyr.scraper.configuration.properties.ScraperProperties;
import com.zephyr.scraper.locator.SearchEngineProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractBrowsingProvider implements SearchEngineProvider, BrowsingProvider {

    @NonNull
    private ScraperProperties.RequestType type;

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @Override
    public Set<SearchEngine> supports() {
        return properties.getScraper().entrySet().stream()
                .filter(e -> e.getValue().getRequestType().equals(type))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
