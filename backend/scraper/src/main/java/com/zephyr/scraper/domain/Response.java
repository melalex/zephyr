package com.zephyr.scraper.domain;

import com.zephyr.data.enums.SearchEngine;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class Response {
    private Task task;
    private SearchEngine provider;
    private List<String> documents;
}