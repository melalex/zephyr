package com.zephyr.task.services.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeteredSearchCriteriaDto {
    private String id;

    @JsonUnwrapped
    private SearchCriteriaDto searchCriteria;
    private LocalDateTime lastHit;
    private LocalDateTime lastUpdate;
    private long hitsCount;
}
