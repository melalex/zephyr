package com.zephyr.scraper.configuration.properties;

import com.zephyr.data.protocol.enums.SearchEngine;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
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

    @Data
    public static class EngineProperties {
        private UrlMapping url;
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
    public static class UrlMapping {
        private String base;
        private String path = StringUtils.EMPTY;
    }

    @Data
    public static class BrowserProperties {
        private long backOff;
        private int retryCount;
    }

    public enum RequestType {
        DIRECT,
        PROXY,
        VPN,
        TOR
    }
}