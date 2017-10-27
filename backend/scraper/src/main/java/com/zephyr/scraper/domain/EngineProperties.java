package com.zephyr.scraper.domain;

import com.zephyr.data.enums.SearchEngine;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EngineProperties {
    private SearchEngine provider;
    private boolean enabled;
    private boolean useProxy;
    private long delay;
    private long errorDelay;
    private int resultCount;
    private int pageSize;
    private String linkSelector;
}