package com.zephyr.rating.domain;

import com.zephyr.data.enums.SearchEngine;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Rating {

    @Id
    private String id;
    private LocalDateTime timestamp;
    private SearchEngine provider;

    private int position;
    private String link;

    private String query;
    private String languageIso;
    private Place place;
    private UserAgent userAgent;

    @Data
    private class UserAgent {
        private String osName;
        private String osVersion;
        private String browserName;
        private String browserVersion;
    }

    @Data
    private class Place {
        private String country;
        private String placeName;
    }
}
