package com.zephyr.task.services.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class SearchCriteriaDto {
    private String id;
    private String query;
    private long place;
    private String languageIso;

    @JsonUnwrapped
    private UserAgentDto userAgent;

    @Data
    private class UserAgentDto {
        private String osName;
        private String osVersion;
        private String browserName;
        private String browserVersion;
    }
}
