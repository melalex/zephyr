package com.zephyr.rating.services.matchers;

import com.zephyr.commons.ObjectUtils;
import com.zephyr.commons.interfaces.Matcher;
import com.zephyr.rating.domain.Query;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.RatingCriteria;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QueryMatcher implements Matcher<RatingCriteria, Rating> {

    @Override
    public boolean matches(RatingCriteria example, Rating target) {
        return checkUrl(example, target)
                && checkProvider(example, target)
                && checkQueries(example.getQuery(), target.getQuery())
                && checkDateRangeBefore(example, target)
                && checkDateRangeAfter(example, target);
    }

    private boolean checkUrl(RatingCriteria example, Rating target) {
        return example.getUrl().equals(target.getUrl());
    }

    private boolean checkQueries(Query example, Query target) {
        return ObjectUtils.equalsOrNull(example.getQuery(), target.getQuery())
                && ObjectUtils.equalsOrNull(example.getLanguageIso(), target.getLanguageIso())
                && ObjectUtils.equalsOrNull(example.getPlace().getCountry(), target.getPlace().getCountry())
                && ObjectUtils.equalsOrNull(example.getPlace().getPlaceName(), target.getPlace().getPlaceName())
                && ObjectUtils.equalsOrNull(example.getUserAgent().getOsName(), target.getUserAgent().getOsName())
                && ObjectUtils.equalsOrNull(example.getUserAgent().getOsVersion(), target.getUserAgent().getOsVersion())
                && ObjectUtils.equalsOrNull(example.getUserAgent().getBrowserName(), target.getUserAgent().getBrowserName())
                && ObjectUtils.equalsOrNull(example.getUserAgent().getBrowserVersion(), target.getUserAgent().getBrowserVersion());
    }

    private boolean checkProvider(RatingCriteria example, Rating target) {
        return example.getEngines().contains(target.getProvider());
    }

    private boolean checkDateRangeBefore(RatingCriteria example, Rating target) {
        return Optional.ofNullable(example.getTo())
                .map(t -> target.getTimestamp().toLocalDate().isBefore(t))
                .orElse(true);
    }

    private boolean checkDateRangeAfter(RatingCriteria example, Rating target) {
        return Optional.ofNullable(example.getFrom())
                .map(t -> target.getTimestamp().toLocalDate().isAfter(t))
                .orElse(true);
    }
}
