package com.zephyr.scraper.crawler;

import com.zephyr.scraper.domain.EngineResponse;

import java.util.List;

@FunctionalInterface
public interface Crawler {

    List<String> crawl(EngineResponse engineResponse);
}
