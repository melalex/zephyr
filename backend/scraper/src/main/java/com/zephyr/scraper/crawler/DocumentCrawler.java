package com.zephyr.scraper.crawler;

import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.scraper.domain.Response;

@FunctionalInterface
public interface DocumentCrawler {

    SearchResultDto crawl(Response document);
}
