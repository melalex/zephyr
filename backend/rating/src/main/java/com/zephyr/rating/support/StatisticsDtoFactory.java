package com.zephyr.rating.support;

import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.RequestCriteria;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Component
public class StatisticsDtoFactory {

    public StatisticsDto create(RequestCriteria requestCriteria, List<Rating> source) {
        var position = source.stream()
                .collect(Collectors.toMap(r -> r.getRequest().getTimestamp(), Rating::getPosition, merger()));

        return new StatisticsDto(requestCriteria.getOriginalCriteriaId(), position);
    }

    @NotNull
    private BinaryOperator<Integer> merger() {
        return (k, v) -> k < v ? k : v;
    }
}
