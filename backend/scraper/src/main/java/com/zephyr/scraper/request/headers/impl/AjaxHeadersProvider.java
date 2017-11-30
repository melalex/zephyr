package com.zephyr.scraper.request.headers.impl;

import com.google.common.net.HttpHeaders;
import com.zephyr.commons.MapUtils;
import com.zephyr.scraper.request.headers.EngineSpecificHeadersProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AjaxHeadersProvider implements EngineSpecificHeadersProvider {
    private static final String ACCEPT = "*/*";

    @Override
    public Map<String, List<String>> provide(String baseUrl) {
        return MapUtils.multiValueMapBuilder()
                .put(HttpHeaders.ACCEPT, ACCEPT)
                .put(HttpHeaders.HOST, baseUrl)
                .put(HttpHeaders.ORIGIN, baseUrl)
                .build();
    }
}