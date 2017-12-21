package com.zephyr.task.services;

import com.zephyr.task.services.dto.SearchCriteriaDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface SearchCriteriaService {

    Flux<SearchCriteriaDto> findAll(Pageable pageable);
}
