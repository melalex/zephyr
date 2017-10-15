package com.zephyr.scraper.crawler.manager;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.crawler.provider.CrawlingProvider;

@FunctionalInterface
public interface CrawlingManager {

    CrawlingProvider manage(SearchEngine searchEngine);
}
