package com.zephyr.scraper.query.provider.impl;

import com.zephyr.commons.PaginationUtils;
import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.query.provider.QueryProvider;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class AbstractQueryProvider implements QueryProvider {
    private SearchEngine searchEngine;
    private boolean isEnabled;
    private int pageSize;
    private int count;

    @Override
    public Mono<Request> provide(Keyword keyword) {
        return isEnabled
                ? Mono.just(createRequest(keyword))
                : Mono.empty();
    }

    private Request createRequest(Keyword keyword) {
        return Request.of(keyword, searchEngine, provideUrl(keyword), providePages(keyword));
    }

    protected List<Map<String, ?>> providePages(Keyword keyword) {
        return PaginationUtils.pagesStream(count, pageSize)
                .map(p -> providePage(keyword, PaginationUtils.startOf(p, pageSize)))
                .collect(Collectors.toList());
    }

    boolean notFirstPage(int start) {
        return start > 0;
    }

    protected abstract String provideUrl(Keyword keyword);

    protected abstract Map<String, ?> providePage(Keyword keyword, int start);
}