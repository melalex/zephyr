package com.zephyr.scraper.loader.fraud;

import com.zephyr.scraper.domain.PageResponse;

public interface FraudAnalyzer {

    void analyze(String response);
}
