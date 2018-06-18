package com.zephyr.scraper.request.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.commons.StreamUtils;
import com.zephyr.commons.interfaces.UidProvider;
import com.zephyr.commons.support.Page;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.headers.HeadersProvider;
import com.zephyr.scraper.request.params.ParamsProvider;
import com.zephyr.scraper.request.provider.RequestProvider;
import com.zephyr.scraper.request.url.UrlProvider;
import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
public class DefaultRequestProvider implements RequestProvider {

    private SearchEngine engine;
    private List<HeadersProvider> headersProviders;
    private ParamsProvider paramsProvider;
    private UrlProvider urlProvider;
    private ScraperConfigurationService configuration;
    private UidProvider uidProvider;

    @Override
    public List<EngineRequest> provide(Query query) {
        var headers = headersProviders.stream()
                .map(HeadersProvider.from(query, urlProvider.provideBaseUrl(query)))
                .flatMap(MapUtils.unwrap())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, StreamUtils.mergeLists()));

        return Stream.iterate(configuration.getFirstPage(engine), Page::hasNext, Page::getNext)
                .map(getPage(query, headers))
                .collect(Collectors.toList());
    }

    private Function<Page, EngineRequest> getPage(Query query, Map<String, List<String>> headers) {
        return p -> EngineRequest.builder()
                .id(uidProvider.provide())
                .query(query)
                .provider(engine)
                .url(urlProvider.provideBaseUrl(query))
                .uri(urlProvider.provideUri(query))
                .headers(headers)
                .params(paramsProvider.provide(query, p))
                .offset(p.getStart())
                .build();
    }
}
