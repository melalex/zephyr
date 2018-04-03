package com.zephyr.scraper.configuration;

import com.zephyr.commons.support.Page;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.properties.RequestType;

import java.time.Duration;

public interface ScraperConfigurationService {

    String getUrl(SearchEngine engine);

    String getBaseUrl(SearchEngine engine);

    String getPath(SearchEngine engine);

    String getLinkSelector(SearchEngine engine);

    Duration getDelay(SearchEngine engine);

    Duration getErrorDelay(SearchEngine engine);

    Page getFirstPage(SearchEngine engine);

    RequestType getRequestType(SearchEngine engine);

    int getRetryCount();

    long getBackOff();
}
