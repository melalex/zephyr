package com.zephyr.scraper.locator.impl;

import com.zephyr.commons.support.DefaultManager;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.locator.SearchEngineManager;
import com.zephyr.scraper.locator.SearchEngineProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultSearchEngineManager<T extends SearchEngineProvider> implements SearchEngineManager<T> {

    private DefaultManager<SearchEngine, T> delegate;

    public static <T extends SearchEngineProvider> DefaultSearchEngineManager<T> of(Collection<T> providers) {
        return new DefaultSearchEngineManager<>(DefaultManager.of(providers));
    }

    @Override
    public T manage(SearchEngine selector) {
        return delegate.manage(selector);
    }
}
