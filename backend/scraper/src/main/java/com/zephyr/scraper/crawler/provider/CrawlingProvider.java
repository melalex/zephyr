package com.zephyr.scraper.crawler.provider;

import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.scraper.domain.Response;

public interface CrawlingProvider {

    SearchResultDto provide(Response response);
}
