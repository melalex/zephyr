package com.zephyr.scraper.crawler.fraud;

import com.zephyr.scraper.domain.EngineResponse;
import org.jsoup.nodes.Document;

@FunctionalInterface
public interface FraudAnalyzer {

    void analyze(EngineResponse response, Document document);
}
