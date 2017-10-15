package com.zephyr.scraper.query.provider.impl;

import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.data.resources.CountryResource;
import com.zephyr.scraper.clients.LocationServiceClient;
import com.zephyr.scraper.query.builder.GoogleQueryBuilder;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
public class GoogleQueryProvider extends AbstractQueryProvider {
    private static final String DEFAULT_SEARCH_ENGINE = "google.com";

    @Setter(onMethod = @__(@Autowired))
    private LocationServiceClient locationServiceClient;

    @Value("${scraper.google.resultCount}")
    private int count;

    public GoogleQueryProvider(@Value("scraper.google.enabled") boolean enabled) {
        super(SearchEngine.GOOGLE, enabled);
    }

    @Override
    protected Mono<String> getUrl(Keyword keyword) {
        return GoogleQueryBuilder.from(getLocalSearchEngine(keyword.getCountryIso()))
                .query(keyword.getWord())
                .languageIso(keyword.getLanguageIso())
                .count(count)
                .build();
    }

    private Mono<String> getLocalSearchEngine(String countryIso) {
        return locationServiceClient.findCountryByIso(countryIso)
                .map(CountryResource::getLocaleGoogle)
                .switchIfEmpty(Mono.just(DEFAULT_SEARCH_ENGINE))
                .doOnError(t -> Mono.just(DEFAULT_SEARCH_ENGINE));
    }
}