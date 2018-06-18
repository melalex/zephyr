package com.zephyr.test;

import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.data.protocol.request.StatisticRequest;
import com.zephyr.test.mocks.PrincipalMock;
import com.zephyr.test.mocks.TimeMachine;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
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
        var result = results.google();
        return new StatisticsDto(criteria.simple().getId(), Map.of(result.getTimestamp(), SIMPLE_GOOGLE_POSITION));
    }

    public StatisticsDto bingFirstAppearance() {
        var result = results.bing();
        var position = Map.of(result.getTimestamp(), SIMPLE_BING_FIRST_APPEARANCE_POSITION);
        return new StatisticsDto(criteria.simple().getId(), position);
    }

    public StatisticsDto bingSecondAppearance() {
        var result = results.bing();
        var position = Map.of(result.getTimestamp(), SIMPLE_BING_SECOND_APPEARANCE_POSITION);
        return new StatisticsDto(criteria.simple().getId(), position);
    }

    public StatisticsDto yahoo() {
        var result = results.yahoo();
        return new StatisticsDto(criteria.simple().getId(), Map.of(result.getTimestamp(), SIMPLE_YAHOO_POSITION));
    }

    public StatisticRequest simpleRequest() {
        var result = new StatisticRequest();
        result.setPrincipal(PrincipalMock.simple());
        result.setTaskId(Tasks.SIMPLE_ID);
        result.setFrom(SIMPLE_FROM);
        return result;
    }
}
