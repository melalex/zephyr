package com.zephyr.data;

import lombok.Data;

@Data
public class Keyword {
    private String word;
    private String countryIso;
    private String place;
    private String languageIso;
    private String userAgent;
    private boolean onlyFromSpecifiedCountry;
}