package com.zephyr.task.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class SearchCriteria {

    @Id
    private String id;

    private String query;
    private long place;
    private String languageIso;
    private UserAgent userAgent;

    @Data
    private class UserAgent {
        private String osName;
        private String osVersion;
        private String browserName;
        private String browserVersion;
    }
}