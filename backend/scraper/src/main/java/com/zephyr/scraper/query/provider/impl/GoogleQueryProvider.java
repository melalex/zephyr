package com.zephyr.scraper.query.provider.impl;

import com.zephyr.data.Keyword;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.query.provider.QueryProvider;
import org.springframework.stereotype.Component;

@Component
public class GoogleQueryProvider implements QueryProvider {

    @Override
    public Request provide(Keyword keyword) {
        return null;
    }
}
