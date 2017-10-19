package com.zephyr.scraper.crawler.provider;

import com.zephyr.data.SearchResult;
import com.zephyr.scraper.domain.Response;

public interface CrawlingProvider {

    SearchResult provide(Response response);
}
