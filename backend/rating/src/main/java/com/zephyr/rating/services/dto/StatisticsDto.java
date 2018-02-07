package com.zephyr.rating.services.dto;

import com.zephyr.data.dto.SearchCriteriaDto;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Map;

@Value(staticConstructor = "of")
public class StatisticsDto {
    private SearchCriteriaDto searchCriteriaDto;
    private Map<LocalDateTime, Integer> position;
}
