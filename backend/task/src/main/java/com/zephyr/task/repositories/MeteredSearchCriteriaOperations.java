package com.zephyr.task.repositories;

import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.MeteredSearchCriteria;
import org.joda.time.ReadablePeriod;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MeteredSearchCriteriaOperations {

    Mono<MeteredSearchCriteria> updateUsage(SearchCriteria searchCriteria);

    Flux<MeteredSearchCriteria> findAllForUpdate(ReadablePeriod relevancePeriod, Pageable pageable);
}
