package com.zephyr.scraper.query.provider.impl;

import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.query.builder.DuckDuckGoQueryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
public class DuckDuckGoQueryProvider extends AbstractQueryProvider {

    public DuckDuckGoQueryProvider(@Value("scraper.duckduckgo.enabled") boolean enabled) {
        super(SearchEngine.DUCKDUCKGO, enabled);
    }

    @Override
    protected Mono<String> getUrl(Keyword keyword) {
        String result = DuckDuckGoQueryBuilder.defaultEngine()
                .query(keyword.getWord())
                .build();

        return Mono.just(result);
    }
}
