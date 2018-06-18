package com.zephyr.test;

import com.zephyr.data.protocol.dto.RatingDto;
import com.zephyr.data.protocol.enums.SearchEngine;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class Ratings {

    public static final String SIMPLE_ID = "bc1f1650-c036-47a2-aeb2-299bb6506ed4";
    public static final int SIMPLE_POSITION = 0;
    public static final SearchEngine SIMPLE_PROVIDER = SearchEngine.GOOGLE;

    private Criteria criteria;

    public RatingDto simple() {
        var result = new RatingDto();
        result.setId(SIMPLE_ID);
        result.setPosition(SIMPLE_POSITION);
        result.setQueryCriteria(criteria.simple());
        result.setUrl(Tasks.SIMPLE_URL);
        result.setProvider(SIMPLE_PROVIDER);
        result.setTimestamp(Results.SIMPLE_TIMESTAMP);

        return result;
    }
}
