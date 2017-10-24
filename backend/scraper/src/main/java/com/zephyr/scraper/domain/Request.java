package com.zephyr.scraper.domain;

import com.zephyr.data.enums.SearchEngine;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@Builder
public class Request {
    private Task task;
    private SearchEngine provider;
    private String baseUrl;
    private String uri;
    private List<PageRequest> pages;
}