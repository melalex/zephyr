package com.zephyr.scraper.query.provider.impl;

import com.zephyr.data.Keyword;
import com.zephyr.data.SearchEngine;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.query.builder.GoogleQueryBuilder;
import com.zephyr.scraper.query.provider.QueryProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class GoogleQueryProvider implements QueryProvider {

    @Value("${request.resultCount}")
    private int count;

    @Override
    public Request provide(Keyword keyword) {
        return Request.of(keyword, SearchEngine.GOOGLE, getUrl(keyword));
    }

    private String getUrl(Keyword keyword) {
        return GoogleQueryBuilder.with("")
                .query(keyword.getWord())
                .languageIso(keyword.getLanguageIso())
                .resultNumber(count)
                .build();
    }
}