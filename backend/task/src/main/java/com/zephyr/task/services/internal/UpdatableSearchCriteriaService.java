package com.zephyr.task.services.internal;

import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.services.MeteredSearchCriteriaService;
import reactor.core.publisher.Flux;

public interface UpdatableSearchCriteriaService extends MeteredSearchCriteriaService {

    Flux<MeteredSearchCriteria> updateSearchCriteria(SearchCriteria searchCriteria);
}
