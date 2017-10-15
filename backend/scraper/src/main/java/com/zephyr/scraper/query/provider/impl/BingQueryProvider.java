package com.zephyr.scraper.query.provider.impl;

import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.query.builder.BingQueryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
public class BingQueryProvider extends AbstractQueryProvider {

    public BingQueryProvider(@Value("scraper.bing.enabled") boolean enabled) {
        super(SearchEngine.BING, enabled);
    }

    @Value("${scraper.bing.resultCount}")
    private int count;

    @Override
    protected Mono<String> getUrl(Keyword keyword) {
        String result = BingQueryBuilder.defaultEngine()
                .query(keyword.getWord())
                .languageIso(keyword.getCountryIso())
                .count(count)
                .build();

        return Mono.just(result);
    }
}
