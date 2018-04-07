package com.zephyr.scraper.request.headers.impl;

import com.google.common.net.HttpHeaders;
import com.zephyr.commons.support.MultiValueMapBuilder;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.headers.HeadersProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AjaxHeadersProvider implements HeadersProvider {

    private static final String ACCEPT = "*/*";

    @Override
    public Map<String, List<String>> provide(Query query, String baseUrl) {
        return MultiValueMapBuilder.create()
                .put(HttpHeaders.ACCEPT, ACCEPT)
                .put(HttpHeaders.HOST, baseUrl)
                .put(HttpHeaders.ORIGIN, baseUrl)
                .build();
    }
}