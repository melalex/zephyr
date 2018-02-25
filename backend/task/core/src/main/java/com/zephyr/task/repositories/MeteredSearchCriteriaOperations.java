package com.zephyr.task.repositories;

import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.MeteredSearchCriteria;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.temporal.TemporalAmount;

public interface MeteredSearchCriteriaOperations {

    Mono<MeteredSearchCriteria> updateUsage(SearchCriteria searchCriteria);

    Flux<MeteredSearchCriteria> findAllForUpdate(TemporalAmount relevancePeriod, Pageable pageable);
}
