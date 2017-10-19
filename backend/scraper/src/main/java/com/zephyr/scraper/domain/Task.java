package com.zephyr.scraper.domain;

import lombok.Data;

@Data
public class Task {
    private String word;
    private String countryIso;
    private String place;
    private String languageIso;
    private String userAgent;
    private boolean onlyFromSpecifiedCountry;

    private String localeYandex;
    private String localeGoogle;
    private String location;
    private long parent;
}
