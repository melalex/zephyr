package com.zephyr.task.services;

import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;

public interface SearchCriteriaService {

    Flux<MeteredSearchCriteria> findAll(Pageable pageable);

    Flux<MeteredSearchCriteria> findAllByExample(SearchCriteria example, Sort sort);

    Flux<SearchCriteria> findAllForUpdate();

    Flux<MeteredSearchCriteria> updateSearchCriteria(SearchCriteria searchCriteria);
}
