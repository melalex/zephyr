package com.zephyr.scraper.crawler.impl;

import com.zephyr.data.SearchResult;
import com.zephyr.scraper.crawler.DocumentCrawler;
import com.zephyr.scraper.crawler.internal.CrawlingManager;
import com.zephyr.scraper.crawler.internal.impl.CrawlingManagerImpl;
import com.zephyr.scraper.domain.ResponseDocument;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentCrawlerImpl implements DocumentCrawler {

    @Getter(onMethod = @__(@Autowired))
    private CrawlingManager manager;

    @Override
    public SearchResult crawl(ResponseDocument document) {
        return manager.manage(document.getProvider()).provide(document);
    }
}