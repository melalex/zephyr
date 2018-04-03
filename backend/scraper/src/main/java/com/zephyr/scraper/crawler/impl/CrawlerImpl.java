package com.zephyr.scraper.crawler.impl;

import com.zephyr.commons.interfaces.Manager;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.crawler.Crawler;
import com.zephyr.scraper.crawler.fraud.FraudAnalysisProvider;
import com.zephyr.scraper.crawler.parsers.ParsingProvider;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.exceptions.FraudException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CrawlerImpl implements Crawler {
    private static final String START_CRAWLING_MSG = "Start crawling of response with id '{}'";
    private static final String START_FRAUD_ANALYSIS_MSG = "Performing Fraud analyze for response with id '{}'";

    @Setter(onMethod = @__(@Autowired))
    private ScraperConfigurationService scraperConfigurationService;

    @Setter(onMethod = @__(@Autowired))
    private Manager<SearchEngine, FraudAnalysisProvider> fraudAnalysisManager;

    @Setter(onMethod = @__(@Autowired))
    private Manager<SearchEngine, ParsingProvider> parsingManager;

    @Override
    public List<String> crawl(EngineResponse response) {
        log.info(START_CRAWLING_MSG, response.getId());

        SearchEngine engine = response.getProvider();
        Document document = Jsoup.parse(response.getBody());
        String linkSelector = scraperConfigurationService.getLinkSelector(engine);

        log.info(START_FRAUD_ANALYSIS_MSG, response.getId());

        if (fraudAnalysisManager.manage(engine).provide(document)) {
            throw new FraudException(response);
        }

        return parsingManager.manage(engine).parse(document, linkSelector);
    }
}