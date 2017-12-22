package com.zephyr.scraper.properties;

import com.zephyr.data.enums.SearchEngine;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@RefreshScope
@ConfigurationProperties
public class ScraperProperties {

    private BrowserProperties browser;
    private Map<SearchEngine, EngineProperties> scraper;

    public EngineProperties getScraper(SearchEngine engine) {
        return scraper.get(engine);
    }

    @Data
    public static class EngineProperties {
        private boolean enabled;
        private boolean saveResponse;
        private long delay;
        private long errorDelay;
        private int first;
        private int resultCount;
        private int pageSize;
        private String linkSelector;
        private RequestType requestType;
    }

    @Data
    public static class BrowserProperties {
        private long backoff;
        private int retryCount;
    }

    public enum RequestType {
        DIRECT,
        PROXY,
        VPN,
        TOR
    }
}