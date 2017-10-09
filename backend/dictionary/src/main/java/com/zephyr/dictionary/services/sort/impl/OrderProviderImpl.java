package com.zephyr.dictionary.services.sort.impl;

import com.zephyr.dictionary.services.sort.OrderProvider;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class OrderProviderImpl implements OrderProvider {
    private static final String HITS_COUNT_FIELD = "hitsCount";
    private static final String LAST_HIT_FIELD = "lastHit";
    private static final String LAST_UPDATE_FIELD = "lastUpdate";

    @Override
    public Sort provide() {
        return Sort.by(
                Sort.Order.desc(HITS_COUNT_FIELD),
                Sort.Order.desc(LAST_HIT_FIELD),
                Sort.Order.asc(LAST_UPDATE_FIELD)
        );
    }
}
