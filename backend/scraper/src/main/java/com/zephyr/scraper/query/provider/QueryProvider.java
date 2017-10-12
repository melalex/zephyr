package com.zephyr.scraper.query.provider;

import com.zephyr.data.Keyword;
import com.zephyr.scraper.domain.Request;

public interface QueryProvider {

    Request provide(Keyword keyword);
}
