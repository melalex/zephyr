package com.zephyr.scraper;

import static java.util.Map.entry;

import com.zephyr.commons.interfaces.Manager;
import com.zephyr.commons.support.DefaultManager;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.crawler.fraud.FraudAnalysisProvider;
import com.zephyr.scraper.crawler.fraud.impl.DefaultFraudAnalysisProvider;
import com.zephyr.scraper.crawler.fraud.impl.GoogleFraudAnalysisProvider;
import com.zephyr.scraper.crawler.parsers.ParsingProvider;
import com.zephyr.scraper.crawler.parsers.impl.BingParsingProvider;
import com.zephyr.scraper.crawler.parsers.impl.DuckDuckGoParsingProvider;
import com.zephyr.scraper.crawler.parsers.impl.GoogleParsingProvider;
import com.zephyr.scraper.crawler.parsers.impl.YahooParsingProvider;
import com.zephyr.scraper.crawler.parsers.impl.YandexParsingProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CrawlerConfiguration {

    @Bean
    public Manager<SearchEngine, ParsingProvider> parsingManager() {
        return DefaultManager.of(parsingMapping());
    }

    @Bean
    public Manager<SearchEngine, FraudAnalysisProvider> fraudAnalysisManager() {
        return DefaultManager.of(fraudAnalysisMapping());
    }

    @Bean
    public Map<SearchEngine, ParsingProvider> parsingMapping() {
        return Map.ofEntries(
                entry(SearchEngine.BING, bingParsingProvider()),
                entry(SearchEngine.DUCKDUCKGO, duckDuckGoParsingProvider()),
                entry(SearchEngine.GOOGLE, googleParsingProvider()),
                entry(SearchEngine.YAHOO, yahooParsingProvider()),
                entry(SearchEngine.YANDEX, yandexParsingProvider())
        );
    }

    @Bean
    public Map<SearchEngine, FraudAnalysisProvider> fraudAnalysisMapping() {
        return Map.ofEntries(
                entry(SearchEngine.BING, defaultFraudAnalysisProvider()),
                entry(SearchEngine.DUCKDUCKGO, defaultFraudAnalysisProvider()),
                entry(SearchEngine.GOOGLE, googleFraudAnalysisProvider()),
                entry(SearchEngine.YAHOO, defaultFraudAnalysisProvider()),
                entry(SearchEngine.YANDEX, defaultFraudAnalysisProvider())
        );
    }

    @Bean
    public ParsingProvider bingParsingProvider() {
        return new BingParsingProvider();
    }

    @Bean
    public ParsingProvider duckDuckGoParsingProvider() {
        return new DuckDuckGoParsingProvider();
    }

    @Bean
    public ParsingProvider googleParsingProvider() {
        return new GoogleParsingProvider();
    }

    @Bean
    public ParsingProvider yahooParsingProvider() {
        return new YahooParsingProvider();
    }

    @Bean
    public ParsingProvider yandexParsingProvider() {
        return new YandexParsingProvider();
    }

    @Bean
    public FraudAnalysisProvider defaultFraudAnalysisProvider() {
        return new DefaultFraudAnalysisProvider();
    }

    @Bean
    public FraudAnalysisProvider googleFraudAnalysisProvider() {
        return new GoogleFraudAnalysisProvider();
    }
}
