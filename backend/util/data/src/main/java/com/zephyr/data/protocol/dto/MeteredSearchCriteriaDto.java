package com.zephyr.data.protocol.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeteredSearchCriteriaDto {
    private String id;
    private SearchCriteriaDto searchCriteria;
    private LocalDateTime lastHit;
    private LocalDateTime lastUpdate;
    private long hitsCount;
}
