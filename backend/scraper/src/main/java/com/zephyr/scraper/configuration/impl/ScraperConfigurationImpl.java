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
        return getEngineProperties(engine).getLinkSelector();
    }

    @Override
    public Duration getDelay(SearchEngine engine) {
        return Duration.ofMillis(getEngineProperties(engine).getDelay());
    }

    @Override
    public Duration getErrorDelay(SearchEngine engine) {
        return Duration.ofMillis(getEngineProperties(engine).getErrorDelay());
    }

    @Override
    public Page getFirstPage(SearchEngine engine) {
        ScraperProperties.EngineProperties engineProperties = getEngineProperties(engine);

        return Page.builder()
                .page(FIRST_PAGE)
                .first(engineProperties.getFirst())
                .pageSize(engineProperties.getPageSize())
                .count(engineProperties.getResultCount())
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

    private ScraperProperties.EngineProperties getEngineProperties(SearchEngine engine) {
        return properties.getScraper().get(engine);
    }
}
