package com.zephyr.task.business.order.impl;

import com.zephyr.task.order.PageableProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SearchCriteriaPageableProvider implements PageableProvider {

    private static final int FIRST_PAGE = 0;

    private static final String HITS_COUNT_FIELD = "hitsCount";
    private static final String LAST_HIT_FIELD = "lastHit";
    private static final String LAST_UPDATE_FIELD = "lastUpdate";

    @Override
    public Pageable provide(int batchSize) {
        return PageRequest.of(FIRST_PAGE, batchSize, sort());
    }

    private Sort sort() {
        return Sort.by(
                Sort.Order.desc(HITS_COUNT_FIELD),
                Sort.Order.desc(LAST_HIT_FIELD),
                Sort.Order.asc(LAST_UPDATE_FIELD)
        );
    }
}
