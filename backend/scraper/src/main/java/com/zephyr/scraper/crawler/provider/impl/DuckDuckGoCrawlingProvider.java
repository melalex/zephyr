package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.EngineResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class DuckDuckGoCrawlingProvider implements CrawlingProvider {
    private static final String WARNING = "DuckDuckGo Crawler not implemented yet";

    @Override
    public List<String> provide(EngineResponse engineResponse) {
        log.warn(WARNING);
        return Collections.emptyList();
    }
}