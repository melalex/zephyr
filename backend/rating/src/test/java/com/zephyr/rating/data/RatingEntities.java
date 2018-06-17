package com.zephyr.rating.data;

import com.zephyr.commons.StreamUtils;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import com.zephyr.test.Results;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class RatingEntities {

    public static final int BING_OFFSET = 1;
    public static final int GOOGLE_OFFSET = 0;
    public static final int YAHOO_FIRST = 1;

    private RequestEntities request;

    public List<Rating> bing() {
        return bing(request.bing());
    }

    public List<Rating> google() {
        return google(request.google());
    }

    public List<Rating> yahoo() {
        return yahoo(request.yahoo());
    }

    public List<Rating> bing(Request request) {
        return simple(request, Results.BING_LINKS, BING_OFFSET);
    }

    public List<Rating> google(Request request) {
        return simple(request, Results.GOOGLE_LINKS, GOOGLE_OFFSET);
    }

    public List<Rating> yahoo(Request request) {
        return simple(request, Results.YAHOO_LINKS, YAHOO_FIRST);
    }

    public List<Rating> simple(Request request, List<String> links, int first) {
        return StreamUtils.zipWithIndexes(links, first)
                .map(r -> new Rating(request, r.getElement(), r.getIndex()))
                .collect(Collectors.toList());
    }
}
