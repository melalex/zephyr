package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.crawler.fraud.FraudAnalyzer;
import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public abstract class AbstractCrawlingProvider implements CrawlingProvider {
    private static final String START_CRAWLING_MSG = "Start crawling of response with id {}";
    private static final String START_FRAUD_ANALIS_MSG = "Performing Fraud analyze for response with id {}";

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private FraudAnalyzer fraudAnalyzer;

    @Override
    public List<String> provide(EngineResponse engineResponse) {
        log.info(START_CRAWLING_MSG, engineResponse.getId());

        SearchEngine engine = engineResponse.getProvider();
        Document document = Jsoup.parse(engineResponse.getBody());
        String linkSelector = scraperProperties
                .getScraper(engine)
                .getLinkSelector();

        log.info(START_CRAWLING_MSG, engineResponse.getId());
        fraudAnalyzer.analyze(engineResponse, document);

        return parse(document, linkSelector);
    }

    protected abstract List<String> parse(Document document, String linkSelector);
}