package com.zephyr.test;

import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.data.protocol.dto.SearchCriteriaDto;
import com.zephyr.data.protocol.dto.UserAgentDto;
import com.zephyr.test.mocks.TimeMachine;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class Criteria {

    public static final String QUERY1 = "QUERY1";
    public static final String QUERY2 = "QUERY2";

    public static final Long HIT_HIGHER_PRIORITY = 100L;
    public static final Long HIT_LOWER_PRIORITY = 1L;

    public static final String SIMPLE_ID = "158dc720-51a4-42d8-a33a-9aa79da027f2";
    public static final String SIMPLE_QUERY = "zephyr";
    public static final String SIMPLE_LANGUAGE_ISO = "en";
    public static final Long SIMPLE_HITS_COUNT = 10L;

    public static final LocalDateTime SIMPLE_LAST_HIT = LocalDateTime.parse("2018-05-02T15:15:00");
    public static final LocalDateTime SIMPLE_LAST_UPDATE = LocalDateTime.parse("2018-04-02T15:15:00");

    public static final String NEW_CRITERIA_ID = "23032214-a3f1-4332-9f48-3289b027589b";
    public static final String NEW_CRITERIA_QUERY = "zephyr";
    public static final String NEW_CRITERIA_LANGUAGE_ISO = "en";
    public static final Long NEW_CRITERIA_HITS_COUNT = 1L;

    public static final String INVALID_PLACE_NAME = "INVALID_PLACE_NAME";
    public static final String INVALID_COUNTRY = "INVALID_COUNTRY";

    public static final LocalDateTime NEW_CRITERIA_LAST_HIT = TimeMachine.canonicalNow();
    public static final LocalDateTime NEW_CRITERIA_LAST_UPDATE = TimeMachine.canonicalNow();


    private Places places;
    private UserAgents userAgents;

    public SearchCriteriaDto simple() {
        var criteria = new SearchCriteriaDto();
        criteria.setId(SIMPLE_ID);
        criteria.setQuery(SIMPLE_QUERY);
        criteria.setLanguageIso(SIMPLE_LANGUAGE_ISO);
        criteria.setHitsCount(SIMPLE_HITS_COUNT);
        criteria.setLastHit(SIMPLE_LAST_HIT);
        criteria.setLastUpdate(SIMPLE_LAST_UPDATE);
        criteria.setUserAgent(toCriteria(userAgents.windowsFirefox()));
        criteria.setPlace(toCriteria(places.kiev()));

        return criteria;
    }

    public SearchCriteriaDto newCriteria() {
        var criteria = new SearchCriteriaDto();
        criteria.setId(NEW_CRITERIA_ID);
        criteria.setQuery(NEW_CRITERIA_QUERY);
        criteria.setLanguageIso(NEW_CRITERIA_LANGUAGE_ISO);
        criteria.setHitsCount(NEW_CRITERIA_HITS_COUNT);
        criteria.setLastHit(NEW_CRITERIA_LAST_HIT);
        criteria.setLastUpdate(NEW_CRITERIA_LAST_UPDATE);
        criteria.setUserAgent(toCriteria(userAgents.macSafari()));
        criteria.setPlace(toCriteria(places.calgary()));

        return criteria;
    }

    public SearchCriteriaDto withInvalidPlace() {
        var simple = simple();
        simple.setUserAgent(toCriteria(userAgents.windowsFirefox()));
        simple.setPlace(invalidPlace());

        return simple;
    }

    private SearchCriteriaDto.Place invalidPlace() {
        var place = new SearchCriteriaDto.Place();
        place.setName(INVALID_PLACE_NAME);
        place.setCountry(INVALID_COUNTRY);

        return place;
    }

    private SearchCriteriaDto.UserAgent toCriteria(UserAgentDto userAgentDto) {
        var userAgent = new SearchCriteriaDto.UserAgent();

        userAgent.setDevice(userAgentDto.getDevice());
        userAgent.setBrowserName(userAgentDto.getBrowserName());
        userAgent.setOsName(userAgentDto.getOsName());

        return userAgent;
    }

    private SearchCriteriaDto.Place toCriteria(PlaceDto placeDto) {
        var place = new SearchCriteriaDto.Place();

        place.setCountry(placeDto.getCountry().getIso());
        place.setName(placeDto.getName());

        return place;
    }
}
