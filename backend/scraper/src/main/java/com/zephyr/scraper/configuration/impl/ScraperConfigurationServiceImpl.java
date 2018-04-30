package com.zephyr.scraper.configuration.impl;

import com.zephyr.commons.support.Page;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.configuration.properties.RequestType;
import com.zephyr.scraper.configuration.properties.ScraperProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ScraperConfigurationServiceImpl implements ScraperConfigurationService {

    private static final int FIRST_PAGE = 0;

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @Override
    public String getUrl(SearchEngine engine) {
        return getBaseUrl(engine) + getPath(engine);
    }

    @Override
    public String getBaseUrl(SearchEngine engine) {
        return getEngineProperties(engine).getUrl().getBase();
    }

    @Override
    public String getPath(SearchEngine engine) {
        return getEngineProperties(engine).getUrl().getPath();
    }

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
    public RequestType getRequestType(SearchEngine engine) {
        return getEngineProperties(engine).getRequestType();
    }

    @Override
    public int getRetryCount() {
        return properties.getBrowser().getRetryCount();
    }

    @Override
    public Duration getBackOff() {
        return Duration.ofMillis(properties.getBrowser().getBackOff());
    }

    private ScraperProperties.EngineProperties getEngineProperties(SearchEngine engine) {
        return properties.getScraper().get(engine);
    }
}
