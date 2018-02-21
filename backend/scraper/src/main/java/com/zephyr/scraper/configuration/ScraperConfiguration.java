package com.zephyr.scraper.configuration;

import com.zephyr.commons.support.Page;
import com.zephyr.data.protocol.enums.SearchEngine;

import java.time.Duration;

public interface ScraperConfiguration {

    String getLinkSelector(SearchEngine searchEngine);

    Duration getDelay(SearchEngine engine);

    Duration getErrorDelay(SearchEngine engine);

    Page getFirstPage(SearchEngine engine);

    int getRetryCount();

    long getBackOff();
}
