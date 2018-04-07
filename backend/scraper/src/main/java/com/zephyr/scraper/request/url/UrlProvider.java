package com.zephyr.scraper.request.url;

import com.zephyr.scraper.domain.Query;

public interface UrlProvider {

    String provideBaseUrl(Query query);

    String provideUri(Query query);
}
