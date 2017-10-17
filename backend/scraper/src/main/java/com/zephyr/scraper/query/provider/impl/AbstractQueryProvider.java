package com.zephyr.scraper.query.provider.impl;

import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.query.provider.QueryProvider;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Map;

@AllArgsConstructor
public abstract class AbstractQueryProvider implements QueryProvider {
    private SearchEngine searchEngine;
    private boolean isEnabled;

    @Override
    public Mono<Request> provide(Keyword keyword) {
        return isEnabled
                ? Mono.just(createRequest(keyword))
                : Mono.empty();
    }

    private Request createRequest(Keyword keyword) {
        return Request.of(keyword, searchEngine, getUrl(keyword), getParams(keyword));
    }

    protected abstract String getUrl(Keyword keyword);

    protected abstract Map<String, ?> getParams(Keyword keyword);
}