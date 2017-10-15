package com.zephyr.scraper.domain;

import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import lombok.Value;

@Value(staticConstructor = "of")
public class Response {
    private Keyword keyword;
    private SearchEngine provider;
    private String html;
}