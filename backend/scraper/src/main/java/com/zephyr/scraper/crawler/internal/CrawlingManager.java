package com.zephyr.scraper.crawler.internal;

import com.zephyr.data.SearchEngine;
import com.zephyr.scraper.crawler.internal.provider.CrawlingProvider;

public interface CrawlingManager {

    CrawlingProvider manage(SearchEngine searchEngine);
}
