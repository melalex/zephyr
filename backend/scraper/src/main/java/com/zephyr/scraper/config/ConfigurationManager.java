package com.zephyr.scraper.config;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.EngineProperties;

@FunctionalInterface
public interface ConfigurationManager {

    EngineProperties configFor(SearchEngine engine);
}
