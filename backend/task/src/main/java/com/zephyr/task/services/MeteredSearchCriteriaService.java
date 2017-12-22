package com.zephyr.task.services;

import com.zephyr.task.services.dto.MeteredSearchCriteriaDto;
import com.zephyr.task.services.dto.SearchCriteriaDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;

public interface MeteredSearchCriteriaService {

    Flux<MeteredSearchCriteriaDto> findAll(Pageable pageable);

    Flux<MeteredSearchCriteriaDto> findByExample(SearchCriteriaDto example, Sort sort);
}
