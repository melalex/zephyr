package com.zephyr.task.data;

import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.data.protocol.dto.UserAgentDto;
import com.zephyr.task.domain.Place;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.UserAgent;
import com.zephyr.test.Criteria;
import com.zephyr.test.Places;
import com.zephyr.test.UserAgents;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class CriteriaEntities {

    private Places places;
    private UserAgents userAgents;

    public SearchCriteria simple() {
        var result =
                newCriteria(Criteria.SIMPLE_QUERY, Criteria.SIMPLE_HITS_COUNT, Criteria.SIMPLE_LAST_UPDATE);

        result.setId(Criteria.SIMPLE_ID);

        return result;
    }

    public SearchCriteria newCriteria(String query, Long hitsCount, LocalDateTime lastUpdate) {
        var criteria = new SearchCriteria();
        criteria.setQuery(query);
        criteria.setHitsCount(hitsCount);
        criteria.setLastUpdate(lastUpdate);
        criteria.setLanguageIso(Criteria.SIMPLE_LANGUAGE_ISO);
        criteria.setLastHit(Criteria.SIMPLE_LAST_HIT);
        criteria.setUserAgent(toCriteria(userAgents.windowsFirefox()));
        criteria.setPlace(toCriteria(places.kiev()));

        return criteria;
    }

    public SearchCriteria newCriteria() {
        var criteria = new SearchCriteria();
        criteria.setId(Criteria.NEW_CRITERIA_ID);
        criteria.setQuery(Criteria.NEW_CRITERIA_QUERY);
        criteria.setLanguageIso(Criteria.NEW_CRITERIA_LANGUAGE_ISO);
        criteria.setHitsCount(Criteria.NEW_CRITERIA_HITS_COUNT);
        criteria.setLastHit(Criteria.NEW_CRITERIA_LAST_HIT);
        criteria.setLastUpdate(Criteria.NEW_CRITERIA_LAST_UPDATE);
        criteria.setUserAgent(toCriteria(userAgents.macSafari()));
        criteria.setPlace(toCriteria(places.calgary()));

        return criteria;
    }

    public SearchCriteria withInvalidPlace() {
        var simple = simple();
        simple.setUserAgent(toCriteria(userAgents.windowsFirefox()));
        simple.setPlace(invalidPlace());

        return simple;
    }

    private Place invalidPlace() {
        var place = new Place();
        place.setPlaceName(Criteria.INVALID_PLACE_NAME);
        place.setCountry(Criteria.INVALID_COUNTRY);

        return place;
    }

    private UserAgent toCriteria(UserAgentDto userAgentDto) {
        var userAgent = new UserAgent();

        userAgent.setDevice(userAgentDto.getDevice());
        userAgent.setBrowserName(userAgentDto.getBrowserName());
        userAgent.setOsName(userAgentDto.getOsName());

        return userAgent;
    }

    private Place toCriteria(PlaceDto placeDto) {
        var place = new Place();

        place.setCountry(placeDto.getCountry().getIso());
        place.setPlaceName(placeDto.getName());

        return place;
    }
}
