package com.zephyr.rating.services.dto;

import com.zephyr.data.dto.SearchCriteriaDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class RatingDto {
    private SearchCriteriaDto searchCriteriaDto;
    private Map<LocalDateTime, Integer> position;
}
