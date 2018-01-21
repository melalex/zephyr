package com.zephyr.task.facades.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SearchCriteriaDto {
    private String id;

    @NotEmpty
    private String query;

    @Valid
    @NotNull
    @JsonUnwrapped
    private Place place;
    private String languageIso;

    @Valid
    @NotNull
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
    public static class UserAgent {

        @NotEmpty
        private String osName;
        private String osVersion;
        private String browserName;
        private String browserVersion;
    }
}
