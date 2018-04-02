package com.zephyr.scraper.request.provider.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.commons.support.Page;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.request.headers.HeadersProvider;
import com.zephyr.scraper.request.params.ParamsProvider;
import com.zephyr.scraper.request.provider.RequestProvider;
import com.zephyr.scraper.request.url.UrlProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestProviderImpl implements RequestProvider {

    @Setter
    private SearchEngine engine;

    @Setter
    private List<HeadersProvider> headersProviders;

    @Setter
    private ParamsProvider paramsProvider;

    @Setter
    private UrlProvider urlProvider;

    @Setter(onMethod = @__(@Autowired))
    private ScraperConfigurationService configuration;

    @Override
    public List<EngineRequest> provide(QueryDto query) {
        Map<String, List<String>> headers = headersProviders.stream()
                .map(HeadersProvider.from(query, urlProvider.provideBaseUrl(query)))
                .flatMap(MapUtils.unwrap())
                .collect(MapUtils.toMap());

        return Stream.iterate(configuration.getFirstPage(engine), Page::isNotLast, Page::getNext)
                .map(getPage(query, headers))
                .collect(Collectors.toList());
    }

    private Function<Page, EngineRequest> getPage(QueryDto query, Map<String, List<String>> headers) {
        return p -> EngineRequest.builder()
                .id(UUID.randomUUID().toString())
                .provider(engine)
                .url(urlProvider.provideFullUrl(query))
                .headers(headers)
                .params(paramsProvider.provide(query, p))
                .offset(p.getStart())
                .build();
    }
}
