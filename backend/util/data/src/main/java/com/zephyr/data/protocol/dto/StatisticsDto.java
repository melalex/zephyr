package com.zephyr.data.protocol.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class StatisticsDto {
    private SearchCriteriaDto criteria;
    private Map<LocalDateTime, Integer> position;
}
