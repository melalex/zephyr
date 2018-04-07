package com.zephyr.scraper.request.url;

import com.zephyr.data.internal.dto.QueryDto;

public interface UrlProvider {

    String provideBaseUrl(QueryDto query);

    String provideUri(QueryDto query);
}
