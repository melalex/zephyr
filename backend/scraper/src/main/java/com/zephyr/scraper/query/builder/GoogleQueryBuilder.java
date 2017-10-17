package com.zephyr.scraper.query.builder;

import com.zephyr.scraper.query.util.QueryUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GoogleQueryBuilder {
    private Mono<String> localeSearchEngine;
    private String languageIso;
    private String query;
    private int count;

    public static GoogleQueryBuilder from(Mono<String> localeSearchEngine) {
        GoogleQueryBuilder builder = new GoogleQueryBuilder();
        builder.localeSearchEngine = localeSearchEngine;

        return builder;
    }

    public GoogleQueryBuilder languageIso(String languageIso) {
        this.languageIso = languageIso;
        return this;
    }

    public GoogleQueryBuilder query(String query) {
        this.query = query;
        return this;
    }

    public GoogleQueryBuilder count(int count) {
        this.count = count;
        return this;
    }

    public Mono<String> build() {
        return localeSearchEngine.map(this::build);
    }

    private String build(String localeSearchEngine) {
        return "https://www." + localeSearchEngine + "/search" +
                "?hl=en" +
                "&lr=" + QueryUtil.encode(languageIso) +
                "&safe=images" +
                "&dcr=0" +
                "&data=hp" +
                "&q=" + QueryUtil.encode(query) +
                "&oq=" + QueryUtil.encode(query) +
                "&ie=UTF-8" +
                "&num=" + count +
                "&pws=0";
    }
}
