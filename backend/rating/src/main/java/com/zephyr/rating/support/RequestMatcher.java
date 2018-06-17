package com.zephyr.rating.support;

import com.zephyr.commons.ObjectUtils;
import com.zephyr.commons.interfaces.Matcher;
import com.zephyr.rating.domain.Place;
import com.zephyr.rating.domain.QueryCriteria;
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
               && checkQueries(example.getQueryCriteria(), target.getQuery());
    }

    private boolean checkQueries(QueryCriteria example, QueryCriteria target) {
        return Objects.isNull(example) || Objects.isNull(target) ||
               (ObjectUtils.equalsOrNull(example.getQuery(), target.getQuery())
                && ObjectUtils.equalsOrNull(example.getLanguageIso(), target.getLanguageIso())
                && checkPlace(example.getPlace(), target.getPlace())
                && checkUserAgent(example.getUserAgent(), target.getUserAgent()));
    }

    private boolean checkPlace(Place example, Place target) {
        return Objects.isNull(example) || Objects.isNull(target) ||
               (ObjectUtils.equalsOrNull(example.getCountry(), target.getCountry())
                && ObjectUtils.equalsOrNull(example.getName(), target.getName()));
    }

    private boolean checkUserAgent(UserAgent example, UserAgent target) {
        return Objects.isNull(example) || Objects.isNull(target) ||
               (ObjectUtils.equalsOrNull(example.getOsName(), target.getOsName())
                && ObjectUtils.equalsOrNull(example.getDevice(), target.getDevice())
                && ObjectUtils.equalsOrNull(example.getBrowserName(), target.getBrowserName()));
    }

    private boolean checkProvider(RequestCriteria example, Request target) {
        return Optional.ofNullable(example)
                .map(RequestCriteria::getEngine)
                .map(e -> e.equals(target.getProvider()))
                .orElse(false);
    }
}
