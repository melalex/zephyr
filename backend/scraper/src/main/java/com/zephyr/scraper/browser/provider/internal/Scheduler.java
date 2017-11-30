package com.zephyr.scraper.browser.provider.internal;

import com.zephyr.data.enums.SearchEngine;

import java.time.Duration;

public interface Scheduler {

    Duration reserve(SearchEngine engine);

    void report(SearchEngine engine);
}
