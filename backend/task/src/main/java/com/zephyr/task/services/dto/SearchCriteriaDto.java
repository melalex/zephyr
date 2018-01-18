package com.zephyr.task.services.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
public class SearchCriteriaDto {
    private String id;

    @NotEmpty
    private String query;

    @Valid
    @JsonUnwrapped
    private Place place;
    private String languageIso;

    @JsonUnwrapped
    private UserAgent userAgent;

    @Data
    public static class Place {

        @NotEmpty
        private String country;

        @NotEmpty
        private String placeName;
    }

    @Data
    private static class UserAgent {
        private String osName;
        private String osVersion;
        private String browserName;
        private String browserVersion;
    }
}
