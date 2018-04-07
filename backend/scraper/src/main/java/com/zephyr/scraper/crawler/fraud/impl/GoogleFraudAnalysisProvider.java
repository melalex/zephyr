package com.zephyr.scraper.crawler.fraud.impl;

import com.zephyr.scraper.crawler.fraud.FraudAnalysisProvider;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GoogleFraudAnalysisProvider implements FraudAnalysisProvider {

    private static final String SELECTOR = "#captcha";

    @Override
    public boolean provide(Document document) {
        return Objects.nonNull(document.select(SELECTOR).first());
    }
}
