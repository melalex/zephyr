package com.zephyr.rating.services.dto.factory;

import com.zephyr.data.dto.SearchCriteriaDto;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.services.dto.RatingDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RatingDtoFactory {

    public RatingDto create(SearchCriteriaDto searchCriteria, List<Rating> source) {
        return RatingDto.of(searchCriteria, position(source));
    }

    private Map<LocalDateTime, Integer> position(List<Rating> source) {
        return source.stream().collect(Collectors.toMap(
                Rating::getTimestamp,
                Rating::getPosition
        ));
    }
}
