package com.zephyr.scraper.locator;

import com.zephyr.commons.interfaces.Manager;
import com.zephyr.data.protocol.enums.SearchEngine;

public interface SearchEngineManager<T extends SearchEngineProvider> extends Manager<SearchEngine, T> {

}
