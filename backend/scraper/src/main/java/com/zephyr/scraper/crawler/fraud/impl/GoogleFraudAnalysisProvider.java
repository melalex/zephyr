package com.zephyr.scraper.crawler.fraud.impl;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.crawler.fraud.FraudAnalysisProvider;
import com.zephyr.scraper.locator.AbstractSearchEngineProvider;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GoogleFraudAnalysisProvider extends AbstractSearchEngineProvider implements FraudAnalysisProvider {
    private static final String SELECTOR = "#captcha";

    public GoogleFraudAnalysisProvider() {
        super(SearchEngine.GOOGLE);
    }

    @Override
    public boolean provide(Document document) {
        return Objects.nonNull(document.select(SELECTOR).first());
    }
}
