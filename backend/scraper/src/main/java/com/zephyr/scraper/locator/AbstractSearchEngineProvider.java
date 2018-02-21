package com.zephyr.scraper.locator;

import com.zephyr.commons.templates.AbstractProvider;
import com.zephyr.data.protocol.enums.SearchEngine;

public abstract class AbstractSearchEngineProvider extends AbstractProvider<SearchEngine> {

    public AbstractSearchEngineProvider(SearchEngine key) {
        super(key);
    }
}
