package com.zephyr.rating.support;

import com.zephyr.commons.ObjectUtils;
import com.zephyr.commons.interfaces.Matcher;
import com.zephyr.rating.domain.Place;
import com.zephyr.rating.domain.Query;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.domain.RequestCriteria;
import com.zephyr.rating.domain.UserAgent;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class RequestMatcher implements Matcher<RequestCriteria, Request> {

    @Override
    public boolean matches(RequestCriteria example, Request target) {
        return checkProvider(example, target)
               && checkQueries(example.getQuery(), target.getQuery())
               && checkDateRangeBefore(example, target)
               && checkDateRangeAfter(example, target);
    }

    private boolean checkQueries(Query example, Query target) {
        return Objects.isNull(example) || Objects.isNull(target) ||
               (ObjectUtils.equalsOrNull(example.getQuery(), target.getQuery())
                && ObjectUtils.equalsOrNull(example.getLanguageIso(), target.getLanguageIso())
                && checkPlace(example.getPlace(), target.getPlace())
                && checkUserAgent(example.getUserAgent(), target.getUserAgent()));
    }

    private boolean checkPlace(Place example, Place target) {
        return Objects.isNull(example) || Objects.isNull(target) ||
               (ObjectUtils.equalsOrNull(example.getCountry(), target.getCountry())
                && ObjectUtils.equalsOrNull(example.getPlaceName(), target.getPlaceName()));
    }

    private boolean checkUserAgent(UserAgent example, UserAgent target) {
        return Objects.isNull(example) || Objects.isNull(target) ||
               (ObjectUtils.equalsOrNull(example.getOsName(), target.getOsName())
                && ObjectUtils.equalsOrNull(example.getOsVersion(), target.getOsVersion())
                && ObjectUtils.equalsOrNull(example.getBrowserName(), target.getBrowserName())
                && ObjectUtils.equalsOrNull(example.getBrowserVersion(), target.getBrowserVersion()));
    }

    private boolean checkProvider(RequestCriteria example, Request target) {
        return example.getEngines().contains(target.getProvider());
    }

    private boolean checkDateRangeBefore(RequestCriteria example, Request target) {
        return Optional.ofNullable(example.getTo())
                .map(t -> target.getTimestamp().toLocalDate().isBefore(t))
                .orElse(true);
    }

    private boolean checkDateRangeAfter(RequestCriteria example, Request target) {
        return Optional.ofNullable(example.getFrom())
                .map(t -> target.getTimestamp().toLocalDate().isAfter(t))
                .orElse(true);
    }
}
