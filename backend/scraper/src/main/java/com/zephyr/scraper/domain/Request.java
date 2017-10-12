package com.zephyr.scraper.domain;

import com.zephyr.data.Keyword;
import com.zephyr.data.SearchEngine;
import lombok.Value;

@Value(staticConstructor = "of")
public class Request {
    private Keyword keyword;
    private SearchEngine provider;
    private String url;
}
