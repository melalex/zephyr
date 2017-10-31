package com.zephyr.scraper.domain;

import com.zephyr.data.enums.SearchEngine;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Request {
    private ScraperTask task;
    private SearchEngine provider;
    private String baseUrl;
    private String uri;
    private List<PageRequest> pages;
}