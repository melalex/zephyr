package com.zephyr.scraper.configuration.impl;

import com.zephyr.commons.support.Page;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.ScraperConfiguration;
import com.zephyr.scraper.configuration.properties.ScraperProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ScraperConfigurationImpl implements ScraperConfiguration {
    private static final int FIRST_PAGE = 0;

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @Override
    public String getLinkSelector(SearchEngine engine) {
        return properties.getScraper(engine).getLinkSelector();
    }

    @Override
    public Duration getDelay(SearchEngine engine) {
        return Duration.ofMillis(properties.getScraper(engine).getDelay());
    }

    @Override
    public Duration getErrorDelay(SearchEngine engine) {
        return Duration.ofMillis(properties.getScraper(engine).getErrorDelay());
    }

    @Override
    public Page getFirstPage(SearchEngine engine) {
        return Page.builder()
                .page(FIRST_PAGE)
                .first(properties.getScraper(engine).getFirst())
                .pageSize(properties.getScraper(engine).getPageSize())
                .count(properties.getScraper(engine).getResultCount())
                .build();
    }

    @Override
    public int getRetryCount() {
        return properties.getBrowser().getRetryCount();
    }

    @Override
    public long getBackOff() {
        return properties.getBrowser().getBackOff();
    }
}
