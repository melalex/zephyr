package com.zephyr.data;

import lombok.Data;

@Data
public class Keyword {
    private String word;
    private String countryIso;
    private String languageIso;
    private boolean onlyFromSpecifiedCountry;
}
