package com.zephyr.scraper.query.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GoogleQueryBuilder {
    private static final String WHITE_SPACE_REGEX = "\\s+";
    private static final String WORDS_SEPARATOR = "+";

    private String localeSearchEngine;
    private String languageIso;
    private String query;
    private int resultNumber;

    public static GoogleQueryBuilder with(String localeSearchEngine) {
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

    public GoogleQueryBuilder resultNumber(int resultNumber) {
        this.resultNumber = resultNumber;
        return this;
    }

    public String build() {
        String queryString = getQuery(query);

        return "https://www." + localeSearchEngine + "/search?hl=en" +
                "&lr=" + languageIso +
                "&safe=images" +
                "&dcr=0" +
                "&source=hp" +
                "&q=" + queryString +
                "&oq=" + queryString +
                "&ie=UTF-8" +
                "&num=" + resultNumber +
                "&pws=0";
    }

    private String getQuery(String query) {
        return query.replaceAll(WHITE_SPACE_REGEX, WORDS_SEPARATOR);
    }
}
