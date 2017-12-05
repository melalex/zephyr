package com.zephyr.scraper.request.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.commons.helpers.Page;
import com.zephyr.data.dto.QueryDto;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.properties.ScraperProperties;
import com.zephyr.scraper.request.provider.RequestProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractRequestProvider implements RequestProvider {
    private static final String DO_NOT_TRACK = "DNT";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";

    private static final int FIRST = 0;

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties properties;

    @NonNull
    private SearchEngine engine;

    @Override
    public List<EngineRequest> provide(QueryDto query) {
        Map<String, List<String>> headers = MapUtils.merge(defaultHeaders(query), provideHeaders(query));
        return Stream.iterate(firstPage(), Page::isNotLast, Page::getNext)
                .map(p -> getPage(query, p, headers))
                .collect(Collectors.toList());
    }

    private EngineRequest getPage(QueryDto query, Page page, Map<String, List<String>> headers) {
        return EngineRequest.builder()
                .id(UUID.randomUUID().toString())
                .provider(engine)
                .url(provideUrl(query))
                .headers(headers)
                .params(provideParams(query, page))
                .offset(page.getStart())
                .build();
    }

    private Page firstPage() {
        return Page.builder()
                .page(FIRST)
                .first(properties.getScraper(engine).getFirst())
                .pageSize(properties.getScraper(engine).getPageSize())
                .count(properties.getScraper(engine).getResultCount())
                .build();
    }

    private Map<String, List<String>> defaultHeaders(QueryDto query) {
        return MapUtils.multiValueMapBuilder()
                .put(HttpHeaders.USER_AGENT, query.getUserAgent().getHeaderValue())
                .put(HttpHeaders.ACCEPT_LANGUAGE, query.getLanguageIso())
                .put(HttpHeaders.ACCEPT_ENCODING, ENCODING)
                .put(HttpHeaders.CONNECTION, KEEP_ALIVE)
                .put(DO_NOT_TRACK, TRUE)
                .build();
    }

    protected abstract String provideUrl(QueryDto query);

    protected abstract Map<String, List<String>> provideHeaders(QueryDto query);

    protected abstract Map<String, List<String>> provideParams(QueryDto query, Page page);
}