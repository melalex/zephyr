package com.zephyr.rating.data;

import com.zephyr.commons.StreamUtils;
import com.zephyr.data.protocol.dto.RatingDto;
import com.zephyr.data.protocol.dto.SearchCriteriaDto;
import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import com.zephyr.test.Criteria;
import com.zephyr.test.Results;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class RatingEntities {

    private Criteria criteria;

    public List<Rating> bing(Request request) {
        return simple(request, Results.BING_LINKS);
    }

    public List<Rating> google(Request request) {
        return simple(request, Results.BING_LINKS);
    }

    public List<Rating> yahoo(Request request) {
        return simple(request, Results.BING_LINKS);
    }

    public List<Rating> simple(Request request, List<String> links) {
        return StreamUtils.zipWithIndexes(links)
                .map(r -> new Rating(request, r.getIndex(), r.getElement()))
                .collect(Collectors.toList());
    }

    public RatingDto toDto(Rating rating) {
        SearchCriteriaDto criteriaDto = criteria.simple();
        RatingDto result = new RatingDto();

        criteriaDto.setId(null);
        criteriaDto.setHitsCount(null);
        criteriaDto.setLastUpdate(null);
        criteriaDto.setLastHit(null);

        result.setId(rating.getId());
        result.setPosition(rating.getPosition());
        result.setProvider(SearchEngine.valueOf(rating.getRequest().getProvider()));
        result.setUrl(rating.getUrl());

        result.setQuery(criteriaDto);
        result.setTimestamp(Results.SIMPLE_TIMESTAMP);

        return result;
    }

}
