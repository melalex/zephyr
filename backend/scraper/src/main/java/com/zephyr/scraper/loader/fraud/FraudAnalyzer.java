package com.zephyr.scraper.loader.fraud;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.PageResponse;

public interface FraudAnalyzer {

    void analyze(SearchEngine engine, PageResponse response);
}
