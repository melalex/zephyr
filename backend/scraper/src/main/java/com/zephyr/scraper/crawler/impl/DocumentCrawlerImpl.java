package com.zephyr.scraper.crawler.impl;

import com.zephyr.data.SearchResult;
import com.zephyr.scraper.crawler.DocumentCrawler;
import com.zephyr.scraper.crawler.manager.CrawlingManager;
import com.zephyr.scraper.domain.ResponseDocument;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentCrawlerImpl implements DocumentCrawler {

    @Setter(onMethod = @__(@Autowired))
    private CrawlingManager manager;

    @Override
    public SearchResult crawl(ResponseDocument document) {
        return manager.manage(document.getProvider()).provide(document);
    }
}