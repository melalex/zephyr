package com.zephyr.data.internal.dto;

import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.data.protocol.dto.UserAgentDto;
import lombok.Data;

@Data
public class QueryDto {

    private String query;
    private String languageIso;
    private PlaceDto place;
    private UserAgentDto userAgent;
}
