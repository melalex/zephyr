package com.zephyr.scraper.crawler.manager.impl;

import com.google.common.collect.ImmutableMap;
import com.zephyr.commons.MapUtils;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.crawler.manager.CrawlingManager;
import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
@RefreshScope
public class CrawlingManagerImpl implements CrawlingManager {
    private Map<SearchEngine, CrawlingProvider> providers;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider defaultCrawlingProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider duckDuckGoCrawlingProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider yandexCrawlingProvider;

    @PostConstruct
    public void init() {
        providers = ImmutableMap.<SearchEngine, CrawlingProvider>builder()
                .put(SearchEngine.GOOGLE, defaultCrawlingProvider)
                .put(SearchEngine.BING, defaultCrawlingProvider)
                .put(SearchEngine.YAHOO, defaultCrawlingProvider)
                .put(SearchEngine.DUCKDUCKGO, duckDuckGoCrawlingProvider)
                .put(SearchEngine.YANDEX, yandexCrawlingProvider)
                .build();
    }

    @Override
    public CrawlingProvider manage(SearchEngine engine) {
        return MapUtils.getOrThrow(providers, engine);
    }
}