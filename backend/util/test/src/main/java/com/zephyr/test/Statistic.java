package com.zephyr.test;

import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.data.protocol.request.StatisticRequest;
import com.zephyr.test.mocks.TimeMachine;
import com.zephyr.test.mocks.PrincipalMock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class Statistic {

    public static final LocalDate SIMPLE_TO = TimeMachine.canonicalNow().toLocalDate();
    public static final LocalDate SIMPLE_FROM = SIMPLE_TO.minusYears(1);

    public static final int SIMPLE_GOOGLE_POSITION = 0;
    public static final int SIMPLE_BING_FIRST_APPEARANCE_POSITION = 3;
    public static final int SIMPLE_BING_SECOND_APPEARANCE_POSITION = 9;
    public static final int SIMPLE_YAHOO_POSITION = 5;

    private Results results;
    private Criteria criteria;

    public StatisticsDto google() {
        SearchResultDto result = results.google();
        return new StatisticsDto(criteria.simple().getId(), Map.of(result.getTimestamp(), SIMPLE_GOOGLE_POSITION));
    }

    public StatisticsDto bingFirstAppearance() {
        SearchResultDto result = results.bing();
        Map<LocalDateTime, Integer> position = Map.of(result.getTimestamp(), SIMPLE_BING_FIRST_APPEARANCE_POSITION);
        return new StatisticsDto(criteria.simple().getId(), position);
    }

    public StatisticsDto bingSecondAppearance() {
        SearchResultDto result = results.bing();
        Map<LocalDateTime, Integer> position = Map.of(result.getTimestamp(), SIMPLE_BING_SECOND_APPEARANCE_POSITION);
        return new StatisticsDto(criteria.simple().getId(), position);
    }

    public StatisticsDto yahoo() {
        SearchResultDto result = results.yahoo();
        return new StatisticsDto(criteria.simple().getId(), Map.of(result.getTimestamp(), SIMPLE_YAHOO_POSITION));
    }

    public StatisticRequest simpleRequest() {
        StatisticRequest result = new StatisticRequest();
        result.setPrincipal(PrincipalMock.simple());
        result.setTaskId(Tasks.SIMPLE_ID);
        result.setFrom(SIMPLE_FROM);
        return result;
    }
}
