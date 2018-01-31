package com.zephyr.task.facades.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.zephyr.data.dto.SearchCriteriaDto;
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
