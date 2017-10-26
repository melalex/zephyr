package com.zephyr.scraper.loader.fraud.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.loader.exceptions.FraudException;
import com.zephyr.scraper.loader.fraud.FraudAnalyzer;
import com.zephyr.scraper.loader.fraud.manager.FraudManager;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FraudAnalyzerImpl implements FraudAnalyzer {

    @Setter(onMethod = @__(@Autowired))
    private FraudManager manager;

    @Override
    public void analyze(String response, SearchEngine engine) {
        if (manager.manage(engine).provide(response)) {
            throw new FraudException(String.format("Fraud detection when scraping %s", engine));
        }
    }
}
