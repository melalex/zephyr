package com.zephyr.rating.services.dto;

import com.zephyr.data.dto.SearchCriteriaDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class RatingDto {
    private Status status;
    private SearchCriteriaDto searchCriteriaDto;
    private Map<LocalDateTime, Integer> position;

    private enum Status {
        UP_TO_DATE,
        NOT_LOADED
    }
}
