package com.zephyr.data.protocol.dto;

import lombok.Data;

import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SearchCriteriaDto {

    private String id;

    private LocalDateTime lastHit;
    private LocalDateTime lastUpdate;
    private Long hitsCount;

    @NotEmpty
    private String query;

    @NotEmpty
    private String languageIso;

    @Valid
    @NotNull
    private Place place;

    @Valid
    private UserAgent userAgent;

    @Data
    public static class Place {

        @NotEmpty
        private String country;

        @NotEmpty
        private String name;
    }

    @Data
    public static class UserAgent {

        @NotEmpty
        private String device;

        @NotEmpty
        private String osName;

        @NotEmpty
        private String browserName;
    }
}
