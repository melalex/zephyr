package com.zephyr.scraper.loader.fraud;

import com.zephyr.data.enums.SearchEngine;

public interface FraudAnalyzer {

    void analyze(String response, SearchEngine engine);
}
