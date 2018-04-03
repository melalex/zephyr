package com.zephyr.scraper.crawler.fraud.impl;

import com.zephyr.scraper.crawler.fraud.FraudAnalysisProvider;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class DefaultFraudAnalysisProvider implements FraudAnalysisProvider {

    @Override
    public boolean provide(Document document) {
        return false;
    }
}
