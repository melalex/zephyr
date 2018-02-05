package com.zephyr.rating.services.matchers;

import com.zephyr.commons.ObjectUtils;
import com.zephyr.commons.interfaces.Matcher;
import com.zephyr.data.dto.SearchCriteriaDto;
import com.zephyr.rating.domain.Query;
import org.springframework.stereotype.Component;

@Component
public class QueryMatcher implements Matcher<SearchCriteriaDto, Query> {

    @Override
    public boolean matches(SearchCriteriaDto criteria, Query query) {
        return ObjectUtils.equalsOrNull(criteria.getQuery(), query.getQuery())
                && ObjectUtils.equalsOrNull(criteria.getLanguageIso(), query.getLanguageIso())
                && ObjectUtils.equalsOrNull(criteria.getPlace().getCountry(), query.getPlace().getCountry())
                && ObjectUtils.equalsOrNull(criteria.getPlace().getPlaceName(), query.getPlace().getPlaceName())
                && ObjectUtils.equalsOrNull(criteria.getUserAgent().getOsName(), query.getUserAgent().getOsName())
                && ObjectUtils.equalsOrNull(criteria.getUserAgent().getOsVersion(), query.getUserAgent().getOsVersion())
                && ObjectUtils.equalsOrNull(criteria.getUserAgent().getBrowserName(), query.getUserAgent().getBrowserName())
                && ObjectUtils.equalsOrNull(criteria.getUserAgent().getBrowserVersion(), query.getUserAgent().getBrowserVersion());
    }
}
