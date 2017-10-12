package com.zephyr.scraper.crawler;

import com.zephyr.data.SearchResult;
import com.zephyr.scraper.domain.ResponseDocument;

public interface DocumentCrawler {

    SearchResult crawl(ResponseDocument document);
}
