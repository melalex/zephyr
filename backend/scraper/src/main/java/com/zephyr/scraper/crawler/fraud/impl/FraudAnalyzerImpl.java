package com.zephyr.scraper.crawler.fraud.impl;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.crawler.fraud.FraudAnalyzer;
import com.zephyr.scraper.crawler.fraud.provider.FraudProvider;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.exceptions.FraudException;
import lombok.Setter;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FraudAnalyzerImpl implements FraudAnalyzer {

    @Setter(onMethod = @__(@Autowired))
    private FraudProvider googleFraudProvider;

    @Setter(onMethod = @__(@Autowired))
    private FraudProvider defaultFraudProvider;

    @Override
    public void analyze(EngineResponse response, Document document) {
        if (manage(response.getProvider()).provide(document)) {
            throw new FraudException(response);
        }
    }

    private FraudProvider manage(SearchEngine searchEngine) {
        switch (searchEngine) {
            case GOOGLE:
                return googleFraudProvider;
            default:
                return defaultFraudProvider;
        }
    }
}
