package com.zephyr.scraper.domain;

import com.zephyr.data.commons.Keyword;
import com.zephyr.data.enums.SearchEngine;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@Builder
public class EngineRequest {
    private Keyword keyword;

    private SearchEngine provider;
    private String baseUrl;
    private String uri;

    private int offset;
    private Map<String, List<String>> params;

    public String getUserAgent() {
        return keyword.getUserAgent();
    }

    public String getLanguageIso() {
        return keyword.getLanguageIso();
    }

    public String getFullUrl() {
        return baseUrl + uri;
    }
}