package com.zephyr.scraper.crawler;

import com.zephyr.data.SearchResult;
import com.zephyr.scraper.domain.Response;

@FunctionalInterface
public interface DocumentCrawler {

    SearchResult crawl(Response document);
}
