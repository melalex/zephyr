package com.zephyr.scraper.crawler.manager.impl;

import com.google.common.collect.ImmutableMap;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.crawler.manager.CrawlingManager;
import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Component
public class CrawlingManagerImpl implements CrawlingManager {
    private Map<SearchEngine, CrawlingProvider> crawlers;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider googleProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider bingProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider yahooProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider yandexProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider duckduckgoProvider;

    @PostConstruct
    public void init() {
        crawlers = ImmutableMap.<SearchEngine, CrawlingProvider>builder()
                .put(SearchEngine.GOOGLE, googleProvider)
                .put(SearchEngine.BING, bingProvider)
                .put(SearchEngine.YAHOO, yahooProvider)
                .put(SearchEngine.YANDEX, yandexProvider)
                .put(SearchEngine.DUCKDUCKGO, duckduckgoProvider)
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