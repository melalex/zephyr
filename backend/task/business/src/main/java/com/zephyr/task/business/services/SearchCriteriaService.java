package com.zephyr.task.business.services;

import com.zephyr.task.domain.SearchCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;

public interface SearchCriteriaService {

    Flux<SearchCriteria> findAll(Pageable pageable);

    Flux<SearchCriteria> findAllByExample(SearchCriteria example, Sort sort);

    Flux<SearchCriteria> findAllForUpdate();

    Flux<SearchCriteria> updateSearchCriteria(SearchCriteria searchCriteria);
}
