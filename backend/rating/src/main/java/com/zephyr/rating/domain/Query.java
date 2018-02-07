package com.zephyr.rating.domain;

import lombok.Data;

@Data
public class Query {
    private String query;
    private String languageIso;
    private Place place;
    private UserAgent userAgent;

    @Data
    public static class Place {
        private String country;
        private String placeName;
    }

    @Data
    public static class UserAgent {
        private String osName;
        private String osVersion;
        private String browserName;
        private String browserVersion;
    }
}
