package com.zephyr.scraper.crawler.provider.config;

import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.crawler.provider.impl.CrawlingProviderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrawlingProviderConfig {

    @Bean
    @RefreshScope
    public CrawlingProvider googleProvider(@Value("scraper.google.linkSelector") String selector) {
        return new CrawlingProviderImpl(selector);
    }

    @Bean
    @RefreshScope
    public CrawlingProvider bingProvider(@Value("scraper.bing.linkSelector") String selector) {
        return new CrawlingProviderImpl(selector);
    }

    @Bean
    @RefreshScope
    public CrawlingProvider yahooProvider(@Value("scraper.yahoo.linkSelector") String selector) {
        return new CrawlingProviderImpl(selector);
    }

    @Bean
    @RefreshScope
    public CrawlingProvider yandexProvider(@Value("scraper.yandex.linkSelector") String selector) {
        return new CrawlingProviderImpl(selector);
    }

    @Bean
    @RefreshScope
    public CrawlingProvider baiduProvider(@Value("scraper.baidu.linkSelector") String selector) {
        return new CrawlingProviderImpl(selector);
    }

    @Bean
    @RefreshScope
    public CrawlingProvider duckduckgoProvider(@Value("scraper.duckduckgo.linkSelector") String selector) {
        return new CrawlingProviderImpl(selector);
    }
}
