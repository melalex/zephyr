package com.zephyr.data;

import lombok.Data;

import java.util.Locale;

@Data
public class Keyword {
    private String word;
    private String countryIso;
    private String languageIso;
}
