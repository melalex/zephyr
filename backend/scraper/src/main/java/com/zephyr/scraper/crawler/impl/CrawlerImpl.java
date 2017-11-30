package com.zephyr.scraper.crawler.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.crawler.Crawler;
import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.EngineResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrawlerImpl implements Crawler {

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider bingCrawlingProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider googleCrawlingProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider yahooCrawlingProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider duckDuckGoCrawlingProvider;

    @Setter(onMethod = @__(@Autowired))
    private CrawlingProvider yandexCrawlingProvider;

    @Override
    public List<String> crawl(EngineResponse engineResponse) {
        return manage(engineResponse.getProvider()).provide(engineResponse);
    }

    private CrawlingProvider manage(SearchEngine provider) {
        switch (provider) {
            case BING:
                return bingCrawlingProvider;
            case GOOGLE:
                return googleCrawlingProvider;
            case YAHOO:
                return yahooCrawlingProvider;
            case DUCKDUCKGO:
                return duckDuckGoCrawlingProvider;
            case YANDEX:
                return yandexCrawlingProvider;
            default:
                throw new IllegalArgumentException(String.format("Crawler doesn't support '%s' engine", provider));
        }
    }
}