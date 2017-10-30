package com.zephyr.scraper.crawler.impl;

import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.scraper.crawler.DocumentCrawler;
import com.zephyr.scraper.crawler.manager.CrawlingManager;
import com.zephyr.scraper.domain.Response;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class DocumentCrawlerImpl implements DocumentCrawler {

    @Setter(onMethod = @__(@Autowired))
    private CrawlingManager manager;

    @Override
    public SearchResultDto crawl(Response document) {
        return manager.manage(document.getProvider()).provide(document);
    }
}