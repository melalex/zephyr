package com.zephyr.scraper.crawler.provider;

import com.zephyr.data.SearchResult;
import com.zephyr.scraper.domain.ResponseDocument;

@FunctionalInterface
public interface CrawlingProvider {

    SearchResult provide(ResponseDocument document);
}
