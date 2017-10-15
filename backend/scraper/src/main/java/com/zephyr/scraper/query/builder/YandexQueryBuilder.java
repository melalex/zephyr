package com.zephyr.scraper.query.builder;

import com.zephyr.scraper.query.util.QueryUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YandexQueryBuilder {
    private static final String DEFAULT_SEARCH_ENGINE = "yandex.ru";

    private String localeSearchEngine;
    private String query;

    public static YandexQueryBuilder from(String localeSearchEngine) {
        YandexQueryBuilder builder = new YandexQueryBuilder();
        builder.localeSearchEngine = localeSearchEngine;

        return builder;
    }

    public static YandexQueryBuilder defaultEngine() {
        return from(DEFAULT_SEARCH_ENGINE);
    }

    public YandexQueryBuilder query(String query) {
        this.query = query;
        return this;
    }

    public String build() {
        return "https://" + localeSearchEngine + "/?" +
                "text=" + QueryUtil.encode(query);
    }
}