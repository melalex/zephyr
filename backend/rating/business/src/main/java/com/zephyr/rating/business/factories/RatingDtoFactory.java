package com.zephyr.rating.business.factories;

import com.zephyr.data.protocol.dto.SearchCriteriaDto;
import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.rating.core.domain.Rating;
import com.zephyr.rating.core.domain.RequestCriteria;
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

    public StatisticsDto create(RequestCriteria requestCriteria, List<Rating> source) {
        StatisticsDto result = new StatisticsDto();
        SearchCriteriaDto criteria = modelMapper.map(requestCriteria.getQuery(), SearchCriteriaDto.class);
        Map<LocalDateTime, Integer> position = source.stream()
                .collect(Collectors.toMap(r -> r.getRequest().getTimestamp(), Rating::getPosition));

        result.setCriteria(criteria);
        result.setPosition(position);

        return result;
    }
}
