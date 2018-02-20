package com.zephyr.scraper.browser.provider.impl;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.provider.AbstractSearchEngineProvider;

public abstract class AbstractBrowsingProvider extends AbstractSearchEngineProvider {

    public AbstractBrowsingProvider(SearchEngine key) {
        super(key);
    }
}
