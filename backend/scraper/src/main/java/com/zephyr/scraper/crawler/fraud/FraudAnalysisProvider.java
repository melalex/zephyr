package com.zephyr.scraper.crawler.fraud;

import com.zephyr.scraper.locator.SearchEngineProvider;
import org.jsoup.nodes.Document;

public interface FraudAnalysisProvider extends SearchEngineProvider {

    boolean provide(Document document);
}
