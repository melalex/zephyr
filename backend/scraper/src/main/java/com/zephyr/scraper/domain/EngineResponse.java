package com.zephyr.scraper.domain;

import com.zephyr.data.enums.SearchEngine;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@Builder
public class EngineResponse {
    private String id;
    private Map<String, List<String>> headers;
    private String body;
    private SearchEngine provider;
}