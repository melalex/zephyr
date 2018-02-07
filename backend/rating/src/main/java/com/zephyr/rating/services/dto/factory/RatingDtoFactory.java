package com.zephyr.rating.services.dto.factory;

import com.zephyr.data.dto.SearchCriteriaDto;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.RatingCriteria;
import com.zephyr.rating.services.dto.StatisticsDto;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RatingDtoFactory {

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper modelMapper;

    public StatisticsDto create(RatingCriteria ratingCriteria, List<Rating> source) {
        SearchCriteriaDto criteria = modelMapper.map(ratingCriteria.getQuery(), SearchCriteriaDto.class);
        Map<LocalDateTime, Integer> position = source.stream()
                .collect(Collectors.toMap(Rating::getTimestamp, Rating::getPosition));

        return StatisticsDto.of(criteria, position);
    }
}
