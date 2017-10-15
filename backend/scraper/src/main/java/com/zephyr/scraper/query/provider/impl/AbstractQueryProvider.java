package com.zephyr.scraper.query.provider.impl;

import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.query.provider.QueryProvider;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public abstract class AbstractQueryProvider implements QueryProvider {
    private SearchEngine searchEngine;
    private boolean isEnabled;

    @Override
    public Mono<Request> provide(Keyword keyword) {
        return isEnabled
                ? getUrl(keyword).map(u -> Request.of(keyword, searchEngine, u))
                : Mono.empty();
    }

    protected abstract Mono<String> getUrl(Keyword keyword);
}
