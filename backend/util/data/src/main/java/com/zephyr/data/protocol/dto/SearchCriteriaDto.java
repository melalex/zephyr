package com.zephyr.data.protocol.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class SearchCriteriaDto {

    private String id;

    private LocalDateTime lastHit;
    private LocalDateTime lastUpdate;
    private long hitsCount;

    @NotEmpty
    private String query;

    @NotEmpty
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
