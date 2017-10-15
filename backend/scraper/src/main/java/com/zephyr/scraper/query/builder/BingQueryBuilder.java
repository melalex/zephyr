package com.zephyr.scraper.query.builder;

import com.zephyr.scraper.query.util.QueryUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BingQueryBuilder {
    private static final String DEFAULT_SEARCH_ENGINE = "www.bing.com";

    private String localeSearchEngine;
    private String query;
    private String languageIso;
    private int count;

    public static BingQueryBuilder from(String localeSearchEngine) {
        BingQueryBuilder builder = new BingQueryBuilder();
        builder.localeSearchEngine = localeSearchEngine;

        return builder;
    }

    public static BingQueryBuilder defaultEngine() {
        return from(DEFAULT_SEARCH_ENGINE);
    }

    public BingQueryBuilder query(String query) {
        this.query = query;
        return this;
    }

    public BingQueryBuilder languageIso(String languageIso) {
        this.languageIso = languageIso;
        return this;
    }

    public BingQueryBuilder count(int count) {
        this.count = count;
        return this;
    }

    public String build() {
        return "https://" + localeSearchEngine + "/search" +
                "?q=" + QueryUtil.encode(query + " language:" + languageIso) +
                "&count=" + count;
    }
}
