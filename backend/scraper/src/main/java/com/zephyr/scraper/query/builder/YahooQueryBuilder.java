package com.zephyr.scraper.query.builder;

import com.zephyr.scraper.query.util.QueryUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YahooQueryBuilder {
    private static final String DEFAULT_SEARCH_ENGINE = "search.yahoo.com";

    private String localeSearchEngine;
    private String query;
    private String encoding = "UTF-8";
    private int count = 30;

    public static YahooQueryBuilder from(String localeSearchEngine) {
        YahooQueryBuilder builder = new YahooQueryBuilder();
        builder.localeSearchEngine = localeSearchEngine;

        return builder;
    }

    public static YahooQueryBuilder defaultEngine() {
        return from(DEFAULT_SEARCH_ENGINE);
    }

    public YahooQueryBuilder query(String query) {
        this.query = query;
        return this;
    }

    public YahooQueryBuilder encoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public YahooQueryBuilder count(int count) {
        this.count = count;
        return this;
    }

    public String build() {
        return "https://" + localeSearchEngine + "/search" +
                "?p=" + QueryUtil.encode(query) +
                "&n=" + count +
                "&ie=" + encoding;
    }
}
