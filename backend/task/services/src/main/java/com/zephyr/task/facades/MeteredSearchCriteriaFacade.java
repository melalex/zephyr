package com.zephyr.task.facades;

import com.zephyr.data.protocol.dto.MeteredSearchCriteriaDto;
import com.zephyr.data.protocol.dto.SearchCriteriaDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MeteredSearchCriteriaFacade {

    Flux<MeteredSearchCriteriaDto> findAll(Pageable pageable);

    Flux<MeteredSearchCriteriaDto> findByExample(Mono<SearchCriteriaDto> example, Sort sort);
}
