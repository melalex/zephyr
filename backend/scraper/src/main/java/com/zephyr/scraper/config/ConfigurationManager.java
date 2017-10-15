package com.zephyr.scraper.config;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.EngineConfig;

@FunctionalInterface
public interface ConfigurationManager {

    EngineConfig configFor(SearchEngine engine);
}
