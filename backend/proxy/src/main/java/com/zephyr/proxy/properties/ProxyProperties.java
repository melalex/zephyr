package com.zephyr.proxy.properties;

import com.zephyr.data.enums.SearchEngine;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "proxy")
public class ProxyProperties {

    private int maxFailsCount;

    private String resetFailedCron;

    private Map<SearchEngine, EngineProperties> engine;

    public EngineProperties getEngineProperties(SearchEngine engine) {
        return this.engine.get(engine);
    }

    @Data
    public static class EngineProperties {
        private long delay;
        private long errorDelay;
    }
}