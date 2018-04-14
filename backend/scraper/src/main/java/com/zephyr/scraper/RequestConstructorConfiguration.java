package com.zephyr.scraper;

import com.zephyr.commons.interfaces.UidProvider;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.request.headers.HeadersProvider;
import com.zephyr.scraper.request.headers.impl.DefaultHeadersProvider;
import com.zephyr.scraper.request.headers.impl.HtmlHeadersProvider;
import com.zephyr.scraper.request.params.ParamsProvider;
import com.zephyr.scraper.request.provider.RequestProvider;
import com.zephyr.scraper.request.provider.impl.DefaultRequestProvider;
import com.zephyr.scraper.request.url.UrlProvider;
import com.zephyr.scraper.request.url.impl.DefaultUrlProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RequestConstructorConfiguration {

    @Setter(onMethod = @__(@Autowired))
    private ScraperConfigurationService configuration;

    @Setter(onMethod = @__(@Autowired))
    private UidProvider uidProvider;

    @Bean
    @RefreshScope
    @ConditionalOnProperty(name = "scraper.bing.enabled", havingValue = "true")
    public RequestProvider bingRequestProvider(ParamsProvider bingParamsProvider) {
        return DefaultRequestProvider.builder()
                .engine(SearchEngine.BING)
                .configuration(configuration)
                .uidProvider(uidProvider)
                .urlProvider(bingUrlProvider())
                .paramsProvider(bingParamsProvider)
                .headersProviders(List.of(defaultHeadersProvider(), htmlHeadersProvider()))
                .build();
    }

    @Bean
    @RefreshScope
    @ConditionalOnProperty(name = "scraper.duckduckgo.enabled", havingValue = "true")
    public RequestProvider duckDuckGoRequestProvider(ParamsProvider duckDuckGoParamsProvider) {
        return DefaultRequestProvider.builder()
                .engine(SearchEngine.DUCKDUCKGO)
                .configuration(configuration)
                .uidProvider(uidProvider)
                .urlProvider(duckDuckGoUrlProvider())
                .paramsProvider(duckDuckGoParamsProvider)
                .headersProviders(List.of(defaultHeadersProvider(), ajaxHeadersProvider()))
                .build();
    }

    @Bean
    @RefreshScope
    @ConditionalOnProperty(name = "scraper.google.enabled", havingValue = "true")
    public RequestProvider googleRequestProvider(ParamsProvider googleParamsProvider,
                                                 UrlProvider googleUrlProvider) {
        return DefaultRequestProvider.builder()
                .engine(SearchEngine.GOOGLE)
                .configuration(configuration)
                .uidProvider(uidProvider)
                .urlProvider(googleUrlProvider)
                .paramsProvider(googleParamsProvider)
                .headersProviders(List.of(defaultHeadersProvider(), htmlHeadersProvider()))
                .build();
    }

    @Bean
    @RefreshScope
    @ConditionalOnProperty(name = "scraper.yahoo.enabled", havingValue = "true")
    public RequestProvider yahooRequestProvider(ParamsProvider yahooParamsProvider) {
        return DefaultRequestProvider.builder()
                .engine(SearchEngine.YAHOO)
                .configuration(configuration)
                .uidProvider(uidProvider)
                .urlProvider(yahooUrlProvider())
                .paramsProvider(yahooParamsProvider)
                .headersProviders(List.of(defaultHeadersProvider(), htmlHeadersProvider()))
                .build();
    }

    @Bean
    @RefreshScope
    @ConditionalOnProperty(name = "scraper.yandex.enabled", havingValue = "true")
    public RequestProvider yandexRequestProvider(ParamsProvider yandexParamsProvider,
                                                 UrlProvider yandexUrlProvider) {
        return DefaultRequestProvider.builder()
                .engine(SearchEngine.YANDEX)
                .configuration(configuration)
                .uidProvider(uidProvider)
                .urlProvider(yandexUrlProvider)
                .paramsProvider(yandexParamsProvider)
                .headersProviders(List.of(defaultHeadersProvider(), ajaxHeadersProvider()))
                .build();
    }

    @Bean
    public HeadersProvider ajaxHeadersProvider() {
        return new HtmlHeadersProvider();
    }

    @Bean
    public HeadersProvider defaultHeadersProvider() {
        return new DefaultHeadersProvider();
    }

    @Bean
    public HeadersProvider htmlHeadersProvider() {
        return new HtmlHeadersProvider();
    }

    @Bean
    public UrlProvider bingUrlProvider() {
        return new DefaultUrlProvider(SearchEngine.BING);
    }

    @Bean
    public UrlProvider duckDuckGoUrlProvider() {
        return new DefaultUrlProvider(SearchEngine.DUCKDUCKGO);
    }

    @Bean
    public UrlProvider yahooUrlProvider() {
        return new DefaultUrlProvider(SearchEngine.YAHOO);
    }
}
