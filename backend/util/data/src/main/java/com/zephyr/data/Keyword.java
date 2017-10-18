package com.zephyr.data;

import lombok.Data;

@Data
public class Keyword {
    private String word;
    private String countryIso;
    private String languageIso;
    private String place;
    private String location;
    private long parent;
    private boolean onlyFromSpecifiedCountry;
}