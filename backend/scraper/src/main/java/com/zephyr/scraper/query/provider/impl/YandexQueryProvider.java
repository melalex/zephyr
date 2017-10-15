package com.zephyr.scraper.query.provider.impl;

import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.query.builder.YandexQueryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
public class YandexQueryProvider extends AbstractQueryProvider {

    public YandexQueryProvider(@Value("scraper.yandex.enabled") boolean enabled) {
        super(SearchEngine.YANDEX, enabled);
    }

    @Override
    protected Mono<String> getUrl(Keyword keyword) {
        String result = YandexQueryBuilder.defaultEngine()
                .query(keyword.getWord())
                .build();

        return Mono.just(result);
    }
}
