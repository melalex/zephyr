package com.zephyr.scraper.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class EngineConfig {
    private boolean enabled;
    private boolean useProxy;
    private int resultCount;
    private String linkSelector;
}
