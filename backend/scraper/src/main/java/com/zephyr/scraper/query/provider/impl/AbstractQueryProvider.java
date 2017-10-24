package com.zephyr.scraper.query.provider.impl;

import com.zephyr.commons.PaginationUtils;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.config.ConfigurationManager;
import com.zephyr.scraper.domain.EngineConfig;
import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Task;
import com.zephyr.scraper.query.provider.QueryProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractQueryProvider implements QueryProvider {
    private static final String ROOT = "/";

    @NonNull
    private SearchEngine searchEngine;

    private EngineConfig engineConfig;

    @Autowired
    public void setConfigurationManager(ConfigurationManager configurationManager) {
        this.engineConfig = configurationManager.configFor(searchEngine);
    }

    @Override
    public Mono<Request> provide(Task task) {
        return engineConfig.isEnabled()
                ? Mono.just(createRequest(task))
                : Mono.empty();
    }

    private Request createRequest(Task task) {
        return Request.builder()
                .task(task)
                .provider(searchEngine)
                .baseUrl(provideBaseUrl(task))
                .uri(provideUri())
                .pages(providePages(task))
                .build();
    }

    private List<PageRequest> providePages(Task task) {
        return PaginationUtils.pagesStream(engineConfig.getResultCount(), engineConfig.getPageSize())
                .map(p -> getPage(task, p))
                .collect(Collectors.toList());
    }

    private PageRequest getPage(Task task, int page) {
        return PageRequest.of(providePage(task, PaginationUtils.startOf(page, engineConfig.getPageSize())), page);
    }

    boolean notFirstPage(int start) {
        return start > 0;
    }

    protected String provideUri() {
        return ROOT;
    }

    protected abstract String provideBaseUrl(Task task);

    protected abstract Map<String, ?> providePage(Task task, int start);
}