package com.zephyr.scraper.domain;

import com.zephyr.data.protocol.enums.PlaceType;
import lombok.Data;

@Data
public class Query {

    private String query;
    private String languageIso;
    private Place place;
    private UserAgent userAgent;

    @Data
    public static class Place {

        private long id;
        private long parent;
        private String name;
        private String canonicalName;
        private Country country;
        private PlaceType type;
        private String uule;
    }

    @Data
    public static class Country {

        private String iso;
        private String name;
        private String localeGoogle;
        private String localeYandex;
    }

    @Data
    public static class UserAgent {

        private String id;
        private String header;
        private String device;
        private String osName;
        private String osVersion;
        private String browserName;
        private String browserVersion;
    }
}
