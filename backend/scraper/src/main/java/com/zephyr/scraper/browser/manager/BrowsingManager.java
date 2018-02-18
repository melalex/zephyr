package com.zephyr.scraper.browser.manager;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.browser.provider.BrowsingProvider;

public interface BrowsingManager {

    BrowsingProvider manage(SearchEngine engine);
}
