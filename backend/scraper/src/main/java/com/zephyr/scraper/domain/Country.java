package com.zephyr.scraper.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Country {
    private String iso;
    private String localeGoogle;
}