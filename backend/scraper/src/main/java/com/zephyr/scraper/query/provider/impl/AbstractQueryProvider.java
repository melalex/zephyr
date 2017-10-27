package com.zephyr.scraper.query.provider.impl;

import com.zephyr.commons.PaginationUtils;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Task;
import com.zephyr.scraper.properties.ScraperProperties;
import com.zephyr.scraper.query.provider.QueryProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractQueryProvider implements QueryProvider {
    private static final String ROOT = "/";

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @NonNull
    private SearchEngine engine;

    @Override
    public Request provide(Task task) {
        return Request.builder()
                .task(task)
                .provider(engine)
                .baseUrl(provideBaseUrl(task))
                .uri(provideUri())
                .pages(providePages(task))
                .build();
    }

    private List<PageRequest> providePages(Task task) {
        return PaginationUtils.pagesStream(engineProperties().getResultCount(), engineProperties().getPageSize())
                .map(p -> getPage(task, p))
                .collect(Collectors.toList());
    }

    private PageRequest getPage(Task task, int page) {
        return PageRequest.of(providePage(task, page, engineProperties().getPageSize()), page);
    }

    private ScraperProperties.EngineProperties engineProperties() {
        return properties.getScraper(engine);
    }

    protected String provideUri() {
        return ROOT;
    }

    protected abstract String provideBaseUrl(Task task);

    protected abstract Map<String, ?> providePage(Task task, int page, int pageSize);
}