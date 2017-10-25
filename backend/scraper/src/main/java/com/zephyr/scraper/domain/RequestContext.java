package com.zephyr.scraper.domain;

import com.zephyr.data.enums.SearchEngine;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RequestContext {
    private Task task;
    private SearchEngine provider;
    private Proxy proxy;
    private String baseUrl;
    private String uri;
    private PageRequest page;
}