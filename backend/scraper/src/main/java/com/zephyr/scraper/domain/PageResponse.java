package com.zephyr.scraper.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class PageResponse {
    private String document;
    private int number;
}
