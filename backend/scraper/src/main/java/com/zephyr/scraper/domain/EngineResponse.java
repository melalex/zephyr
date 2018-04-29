package com.zephyr.scraper.domain;

import com.zephyr.data.protocol.enums.SearchEngine;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@Builder
public class EngineResponse {

    private String id;
    private int status;
    private SearchEngine provider;
    private Map<String, List<String>> headers;
    private String body;
}