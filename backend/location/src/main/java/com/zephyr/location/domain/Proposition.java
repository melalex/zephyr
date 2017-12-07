package com.zephyr.location.domain;

import lombok.Data;

@Data
public class Proposition {
    private String word;
    private String country;
    private String countryIso;
    private String language;
    private String languageIso;
}