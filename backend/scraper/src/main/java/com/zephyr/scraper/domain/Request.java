package com.zephyr.scraper.domain;

import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value(staticConstructor = "of")
public class Request {
    private Keyword keyword;
    private SearchEngine provider;
    private String url;
    private List<Map<String, ?>> pages;
}
