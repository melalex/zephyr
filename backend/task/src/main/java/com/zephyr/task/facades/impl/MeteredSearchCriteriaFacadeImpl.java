package com.zephyr.task.facades.impl;

import com.zephyr.commons.anotations.Facade;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.facades.MeteredSearchCriteriaFacade;
import com.zephyr.task.facades.dto.MeteredSearchCriteriaDto;
import com.zephyr.data.dto.SearchCriteriaDto;
import com.zephyr.task.services.SearchCriteriaService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Facade
public class MeteredSearchCriteriaFacadeImpl implements MeteredSearchCriteriaFacade {

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaService searchCriteriaService;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Flux<MeteredSearchCriteriaDto> findAll(Pageable pageable) {
        return searchCriteriaService.findAll(pageable)
                .map(mapper.mapperFor(MeteredSearchCriteriaDto.class));
    }

    @Override
    public Flux<MeteredSearchCriteriaDto> findByExample(Mono<SearchCriteriaDto> example, Sort sort) {
        return example.map(mapper.mapperFor(SearchCriteria.class))
                .flatMapMany(e -> searchCriteriaService.findAllByExample(e, sort))
                .map(mapper.mapperFor(MeteredSearchCriteriaDto.class));
    }
}
