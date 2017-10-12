package com.zephyr.scraper.crawler.internal.impl;

import com.google.common.collect.ImmutableMap;
import com.zephyr.data.SearchEngine;
import com.zephyr.scraper.crawler.internal.CrawlingManager;
import com.zephyr.scraper.crawler.internal.provider.CrawlingProvider;
import com.zephyr.scraper.crawler.internal.provider.impl.GoogleProvider;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Component
public class CrawlingManagerImpl implements CrawlingManager {
    private Map<SearchEngine, CrawlingProvider> crawlers;

    @PostConstruct
    public void init() {
        crawlers = ImmutableMap.<SearchEngine, CrawlingProvider>builder()
                .put(SearchEngine.GOOGLE, new GoogleProvider())
                .build();
    }

    @Override
    public CrawlingProvider manage(SearchEngine searchEngine) {
        return Optional.ofNullable(crawlers.get(searchEngine))
                .orElseThrow(() -> new IllegalArgumentException(errorMessage(searchEngine)));
    }

    private String errorMessage(SearchEngine searchEngine) {
        return String.format("Unknown search engine '%s'", searchEngine);
    }
}
