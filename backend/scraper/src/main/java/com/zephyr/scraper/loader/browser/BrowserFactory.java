package com.zephyr.scraper.loader.browser;

import com.zephyr.scraper.domain.Request;

public interface BrowserFactory {

    Browser create(Request request);
}
