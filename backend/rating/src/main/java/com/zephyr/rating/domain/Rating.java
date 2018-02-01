package com.zephyr.rating.domain;

import com.zephyr.data.enums.SearchEngine;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
@Builder(toBuilder = true)
public class Rating {

    @Id
    private String id;
    private Query query;
    private LocalDateTime timestamp;
    private SearchEngine provider;

    private int position;
    private String link;

    public Rating withLinkAndPosition(String link, int position) {
        return toBuilder()
                .link(link)
                .position(position)
                .build();
    }

    @Data
    public static class Query {
        private String query;
        private String languageIso;
        private Place place;
        private UserAgent userAgent;
    }

    @Data
    private static class UserAgent {
        private String osName;
        private String osVersion;
        private String browserName;
        private String browserVersion;
    }

    @Data
    private static class Place {
        private String country;
        private String placeName;
    }
}
