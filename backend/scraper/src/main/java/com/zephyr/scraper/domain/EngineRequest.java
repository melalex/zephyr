package com.zephyr.scraper.domain;

import com.zephyr.data.dto.QueryDto;
import com.zephyr.data.enums.SearchEngine;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@Builder
public class EngineRequest {
    private QueryDto query;

    private SearchEngine provider;
    private String url;

    private int offset;
    private Map<String, List<String>> headers;
    private Map<String, List<String>> params;
}