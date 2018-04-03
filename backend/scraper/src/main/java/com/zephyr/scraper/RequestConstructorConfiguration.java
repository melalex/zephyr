package com.zephyr.scraper;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.request.headers.HeadersProvider;
import com.zephyr.scraper.request.headers.impl.DefaultHeadersProvider;
import com.zephyr.scraper.request.headers.impl.HtmlHeadersProvider;
import com.zephyr.scraper.request.params.ParamsProvider;
import com.zephyr.scraper.request.provider.RequestProvider;
import com.zephyr.scraper.request.provider.impl.RequestProviderImpl;
import com.zephyr.scraper.request.url.UrlProvider;
import com.zephyr.scraper.request.url.impl.DefaultUrlProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RequestConstructorConfiguration {

    @Setter(onMethod = @__(@Autowired))
    private ScraperConfigurationService configuration;

    @Bean
    public RequestProvider bingRequestProvider(ParamsProvider bingParamsProvider) {
        RequestProviderImpl bean = new RequestProviderImpl();
        bean.setEngine(SearchEngine.BING);
        bean.setConfiguration(configuration);
        bean.setUrlProvider(bingUrlProvider());
        bean.setParamsProvider(bingParamsProvider);
        bean.setHeadersProviders(List.of(defaultHeadersProvider(), htmlHeadersProvider()));

        return bean;
    }

    @Bean
    public RequestProvider duckDuckGoRequestProvider(ParamsProvider duckDuckGoParamsProvider) {
        RequestProviderImpl bean = new RequestProviderImpl();
        bean.setEngine(SearchEngine.DUCKDUCKGO);
        bean.setConfiguration(configuration);
        bean.setUrlProvider(duckDuckGoUrlProvider());
        bean.setParamsProvider(duckDuckGoParamsProvider);
        bean.setHeadersProviders(List.of(defaultHeadersProvider(), ajaxHeadersProvider()));

        return bean;
    }

    @Bean
    public RequestProvider googleRequestProvider(ParamsProvider googleParamsProvider,
                                                 UrlProvider googleUrlProvider) {
        RequestProviderImpl bean = new RequestProviderImpl();
        bean.setEngine(SearchEngine.GOOGLE);
        bean.setConfiguration(configuration);
        bean.setUrlProvider(googleUrlProvider);
        bean.setParamsProvider(googleParamsProvider);
        bean.setHeadersProviders(List.of(defaultHeadersProvider(), htmlHeadersProvider()));

        return bean;
    }

    @Bean
    public RequestProvider yahooRequestProvider(ParamsProvider yahooParamsProvider) {
        RequestProviderImpl bean = new RequestProviderImpl();
        bean.setEngine(SearchEngine.YAHOO);
        bean.setConfiguration(configuration);
        bean.setUrlProvider(yahooUrlProvider());
        bean.setParamsProvider(yahooParamsProvider);
        bean.setHeadersProviders(List.of(defaultHeadersProvider(), htmlHeadersProvider()));

        return bean;
    }

    @Bean
    public RequestProvider yandexRequestProvider(ParamsProvider yandexParamsProvider,
                                                 UrlProvider yandexUrlProvider) {
        RequestProviderImpl bean = new RequestProviderImpl();
        bean.setEngine(SearchEngine.YANDEX);
        bean.setConfiguration(configuration);
        bean.setUrlProvider(yandexUrlProvider);
        bean.setParamsProvider(yandexParamsProvider);
        bean.setHeadersProviders(List.of(defaultHeadersProvider(), ajaxHeadersProvider()));

        return bean;
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
