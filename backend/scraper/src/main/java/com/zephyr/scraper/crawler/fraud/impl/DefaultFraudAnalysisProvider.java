package com.zephyr.scraper.crawler.fraud.impl;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.crawler.fraud.FraudAnalysisProvider;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DefaultFraudAnalysisProvider implements FraudAnalysisProvider {

    @Override
    public boolean provide(Document document) {
        return false;
    }

    @Override
    public Set<SearchEngine> supports() {
        return Set.of(SearchEngine.YAHOO, SearchEngine.BING, SearchEngine.DUCKDUCKGO, SearchEngine.YANDEX);
    }
}
