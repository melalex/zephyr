package com.zephyr.task.repositories;

import com.zephyr.task.domain.SearchCriteria;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.temporal.TemporalAmount;

public interface SearchCriteriaOperations {

    Mono<SearchCriteria> updateUsage(SearchCriteria searchCriteria);

    Flux<SearchCriteria> findAllForUpdate(TemporalAmount relevancePeriod, Pageable pageable);
}
