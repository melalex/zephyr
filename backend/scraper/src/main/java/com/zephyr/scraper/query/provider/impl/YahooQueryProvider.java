package com.zephyr.scraper.query.provider.impl;

import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.query.builder.YahooQueryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
public class YahooQueryProvider extends AbstractQueryProvider {

    public YahooQueryProvider(@Value("scraper.yahoo.enabled") boolean enabled) {
        super(SearchEngine.YAHOO, enabled);
    }

    @Value("${scraper.yahoo.resultCount}")
    private int count;

    @Override
    protected Mono<String> getUrl(Keyword keyword) {
        String result = YahooQueryBuilder.defaultEngine()
                .query(keyword.getWord())
                .count(count)
                .build();

        return Mono.just(result);
    }
}