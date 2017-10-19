package com.zephyr.scraper.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EngineConfig {
    private boolean enabled;
    private boolean useProxy;
    private long delay;
    private int resultCount;
    private int pageSize;
    private String linkSelector;
}