package com.zephyr.scraper.config.impl;

import com.google.common.collect.ImmutableMap;
import com.zephyr.commons.MapUtils;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.config.ConfigurationManager;
import com.zephyr.scraper.domain.EngineProperties;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
@RefreshScope
public class ConfigurationManagerImpl implements ConfigurationManager {
    private Map<SearchEngine, EngineProperties> configs;

    @Setter(onMethod = @__(@Autowired))
    private Environment environment;

    @PostConstruct
    public void init() {
        configs = ImmutableMap.<SearchEngine, EngineProperties>builder()
                .put(SearchEngine.GOOGLE, createGoogleConfig())
                .put(SearchEngine.BING, createBingConfig())
                .put(SearchEngine.YAHOO, createYahooConfig())
                .put(SearchEngine.YANDEX, createYandexConfig())
                .put(SearchEngine.DUCKDUCKGO, createDuckDuckGoConfig())
                .build();
    }

    @Override
    public EngineProperties configFor(SearchEngine engine) {
        return MapUtils.getOrThrow(configs, engine);
    }

    private EngineProperties createGoogleConfig() {
        return EngineProperties.builder()
                .enabled(environment.getProperty("scraper.google.enabled", Boolean.class, false))
                .useProxy(environment.getProperty("scraper.google.useProxy", Boolean.class, false))
                .resultCount(environment.getProperty("scraper.google.resultCount", Integer.class, 10))
                .pageSize(environment.getProperty("scraper.google.pageSize", Integer.class, 10))
                .delay(environment.getProperty("scraper.google.delay", Long.class, 0L))
                .linkSelector(environment.getProperty("scraper.google.linkSelector", StringUtils.EMPTY))
                .build();
    }

    private EngineProperties createBingConfig() {
        return EngineProperties.builder()
                .enabled(environment.getProperty("scraper.bing.enabled", Boolean.class, false))
                .useProxy(environment.getProperty("scraper.bing.useProxy", Boolean.class, false))
                .resultCount(environment.getProperty("scraper.bing.resultCount", Integer.class, 10))
                .pageSize(environment.getProperty("scraper.bing.pageSize", Integer.class, 10))
                .delay(environment.getProperty("scraper.bing.delay", Long.class, 0L))
                .linkSelector(environment.getProperty("scraper.bing.linkSelector", StringUtils.EMPTY))
                .build();
    }

    private EngineProperties createYahooConfig() {
        return EngineProperties.builder()
                .enabled(environment.getProperty("scraper.yahoo.enabled", Boolean.class, false))
                .useProxy(environment.getProperty("scraper.yahoo.useProxy", Boolean.class, false))
                .resultCount(environment.getProperty("scraper.yahoo.resultCount", Integer.class, 10))
                .pageSize(environment.getProperty("scraper.yahoo.pageSize", Integer.class, 10))
                .delay(environment.getProperty("scraper.yahoo.delay", Long.class, 0L))
                .linkSelector(environment.getProperty("scraper.yahoo.linkSelector", StringUtils.EMPTY))
                .build();
    }

    private EngineProperties createYandexConfig() {
        return EngineProperties.builder()
                .enabled(environment.getProperty("scraper.yandex.enabled", Boolean.class, false))
                .useProxy(environment.getProperty("scraper.yandex.useProxy", Boolean.class, false))
                .resultCount(environment.getProperty("scraper.yandex.resultCount", Integer.class, 10))
                .pageSize(environment.getProperty("scraper.yandex.pageSize", Integer.class, 10))
                .delay(environment.getProperty("scraper.yandex.delay", Long.class, 0L))
                .linkSelector(environment.getProperty("scraper.yandex.linkSelector", StringUtils.EMPTY))
                .build();
    }

    private EngineProperties createDuckDuckGoConfig() {
        return EngineProperties.builder()
                .enabled(environment.getProperty("scraper.duckduckgo.enabled", Boolean.class, false))
                .useProxy(environment.getProperty("scraper.duckduckgo.useProxy", Boolean.class, false))
                .resultCount(environment.getProperty("scraper.duckduckgo.resultCount", Integer.class, 10))
                .pageSize(environment.getProperty("scraper.duckduckgo.pageSize", Integer.class, 10))
                .delay(environment.getProperty("scraper.duckduckgo.delay", Long.class, 0L))
                .linkSelector(environment.getProperty("scraper.duckduckgo.linkSelector", StringUtils.EMPTY))
                .build();
    }
}
