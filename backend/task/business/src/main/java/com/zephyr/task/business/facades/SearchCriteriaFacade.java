package com.zephyr.task.business.facades;

import com.zephyr.data.protocol.dto.SearchCriteriaDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SearchCriteriaFacade {

    Flux<SearchCriteriaDto> findAll(Pageable pageable);

    Flux<SearchCriteriaDto> findByExample(Mono<SearchCriteriaDto> example, Sort sort);
}
