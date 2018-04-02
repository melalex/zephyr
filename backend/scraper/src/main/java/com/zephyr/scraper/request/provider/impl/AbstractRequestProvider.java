package com.zephyr.scraper.request.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.commons.support.MultiValueMapBuilder;
import com.zephyr.commons.support.Page;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.ScraperConfiguration;
import com.zephyr.scraper.domain.EngineRequest;
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

    @Setter(onMethod = @__(@Autowired))
    private ScraperConfiguration configuration;

    @NonNull
    private SearchEngine engine;

    @Override
    public List<EngineRequest> provide(QueryDto query) {
        Map<String, List<String>> headers = MapUtils.merge(defaultHeaders(query), provideHeaders(query));
        return Stream.iterate(configuration.getFirstPage(engine), Page::isNotLast, Page::getNext)
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

    private Map<String, List<String>> defaultHeaders(QueryDto query) {
        return MultiValueMapBuilder.create()
                .put(HttpHeaders.USER_AGENT, query.getUserAgent().getHeader())
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