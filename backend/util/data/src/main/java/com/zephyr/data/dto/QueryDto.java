package com.zephyr.data.dto;

import lombok.Data;

@Data
public class QueryDto {
    private String query;
    private String languageIso;
    private PlaceDto place;
    private UserAgentDto userAgent;
}
