package com.zephyr.scraper.crawler.fraud;

import org.jsoup.nodes.Document;

public interface FraudAnalysisProvider {

    boolean provide(Document document);
}
