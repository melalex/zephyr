package com.zephyr.data.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SearchCriteriaDto {
    private String id;

    @NotEmpty
    private String query;
    private String languageIso;

    @Valid
    @NotNull
    private Place place;

    @Valid
    @NotNull
    private UserAgent userAgent;

    @Data
    public static class Place {

        @NotEmpty
        private String country;

        @NotEmpty
        private String placeName;
    }

    @Data
    public static class UserAgent {

        @NotEmpty
        private String osName;
        private String osVersion;
        private String browserName;
        private String browserVersion;
    }
}
