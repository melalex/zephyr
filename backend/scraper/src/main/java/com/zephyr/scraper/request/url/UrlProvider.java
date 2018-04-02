package com.zephyr.scraper.request.url;

import com.zephyr.data.internal.dto.QueryDto;

@FunctionalInterface
public interface UrlProvider {

    String provideBaseUrl(QueryDto query);

    default String provideFullUrl(QueryDto query) {
        return provideBaseUrl(query);
    }
}
