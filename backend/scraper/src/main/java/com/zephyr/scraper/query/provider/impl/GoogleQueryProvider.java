package com.zephyr.scraper.query.provider.impl;

import com.zephyr.data.Keyword;
import com.zephyr.data.SearchEngine;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.query.provider.QueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class GoogleQueryProvider implements QueryProvider {
    private static final String URL_PATTERN =
            "https://www.%s/search?hl=en&dcr=0&source=hp&q=%s&oq=%s&ie=UTF-8&num=%s";
    private static final String WHITE_SPACE_REGEX = "\\s+";
    private static final String WORDS_SEPARATOR = "+";

    @Value("${request.resultCount}")
    private int count;

    @Override
    public Request provide(Keyword keyword) {
        return Request.of(keyword, SearchEngine.GOOGLE, getUrl(keyword));
    }

    private String getUrl(Keyword keyword) {
        String query = getQuery(keyword);
        return String.format(URL_PATTERN, keyword.getCountryIso(), query, query, count);
    }

    private String getQuery(Keyword keyword) {
        return keyword.getWord().replaceAll(WHITE_SPACE_REGEX, WORDS_SEPARATOR);
    }
}