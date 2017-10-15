package com.zephyr.scraper.config.impl;

import com.google.common.collect.ImmutableMap;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.config.ConfigurationManager;
import com.zephyr.scraper.domain.EngineConfig;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Component
@RefreshScope
public class ConfigurationManagerImpl implements ConfigurationManager {
    private Map<SearchEngine, EngineConfig> configs;

    @Setter(onMethod = @__(@Autowired))
    private Environment environment;

    @PostConstruct
    public void init() {
        configs = ImmutableMap.<SearchEngine, EngineConfig>builder()
                .put(SearchEngine.GOOGLE, createGoogleConfig())
                .put(SearchEngine.BING, createBingConfig())
                .put(SearchEngine.YAHOO, createYahooConfig())
                .put(SearchEngine.YANDEX, createYandexConfig())
                .put(SearchEngine.DUCKDUCKGO, createDuckDuckGoConfig())
                .build();
    }

    @Override
    public EngineConfig configFor(SearchEngine engine) {
        return Optional.ofNullable(configs.get(engine))
                .orElseThrow(() -> new IllegalArgumentException(errorMessage(engine)));
    }

    private String errorMessage(SearchEngine searchEngine) {
        return String.format("Unknown search engine '%s'", searchEngine);
    }

    private EngineConfig createGoogleConfig() {
        boolean enabled = environment.getProperty("scraper.google.enabled", Boolean.class);
        boolean useProxy = environment.getProperty("scraper.google.linkSelector", Boolean.class);
        int count = environment.getProperty("scraper.google.resultCount", Integer.class);
        String selector = environment.getProperty("scraper.google.useProxy");

        return EngineConfig.of(enabled, useProxy, count, selector);
    }

    private EngineConfig createBingConfig() {
        boolean enabled = environment.getProperty("scraper.bing.enabled", Boolean.class);
        boolean useProxy = environment.getProperty("scraper.bing.linkSelector", Boolean.class);
        int count = environment.getProperty("scraper.bing.resultCount", Integer.class);
        String selector = environment.getProperty("scraper.bing.useProxy");

        return EngineConfig.of(enabled, useProxy, count, selector);
    }

    private EngineConfig createYahooConfig() {
        boolean enabled = environment.getProperty("scraper.yahoo.enabled", Boolean.class);
        boolean useProxy = environment.getProperty("scraper.yahoo.linkSelector", Boolean.class);
        int count = environment.getProperty("scraper.yahoo.resultCount", Integer.class);
        String selector = environment.getProperty("scraper.yahoo.useProxy");

        return EngineConfig.of(enabled, useProxy, count, selector);
    }

    private EngineConfig createYandexConfig() {
        boolean enabled = environment.getProperty("scraper.yandex.enabled", Boolean.class);
        boolean useProxy = environment.getProperty("scraper.yandex.linkSelector", Boolean.class);
        int count = environment.getProperty("scraper.yandex.resultCount", Integer.class);
        String selector = environment.getProperty("scraper.yandex.useProxy");

        return EngineConfig.of(enabled, useProxy, count, selector);
    }

    private EngineConfig createDuckDuckGoConfig() {
        boolean enabled = environment.getProperty("scraper.duckduckgo.enabled", Boolean.class);
        boolean useProxy = environment.getProperty("scraper.duckduckgo.linkSelector", Boolean.class);
        int count = environment.getProperty("scraper.duckduckgo.resultCount", Integer.class);
        String selector = environment.getProperty("scraper.duckduckgo.useProxy");

        return EngineConfig.of(enabled, useProxy, count, selector);
    }
}
