package com.zephyr.scraper.request.headers.impl;

import com.zephyr.commons.MapUtils;
import com.zephyr.scraper.request.headers.EngineSpecificHeadersProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class HtmlHeadersProvider implements EngineSpecificHeadersProvider {
    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String TRUE = "1";

    @Override
    public Map<String, List<String>> provide(String baseUrl) {
        return MapUtils.multiValueMapBuilder()
                .put(HttpHeaders.REFERER, baseUrl)
                .put(com.google.common.net.HttpHeaders.ACCEPT, ACCEPT)
                .put(UPGRADE_INSECURE_REQUESTS, TRUE)
                .build();
    }
}