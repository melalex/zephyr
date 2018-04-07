package com.zephyr.scraper.domain;

import com.zephyr.data.protocol.enums.SearchEngine;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@Builder
public class EngineRequest {
    private String id;

    private Query query;

    private SearchEngine provider;
    private String url;
    private String uri;

    private int offset;
    private Map<String, List<String>> headers;
    private Map<String, List<String>> params;

    public String getFullPath() {
        return url + uri;
    }
}