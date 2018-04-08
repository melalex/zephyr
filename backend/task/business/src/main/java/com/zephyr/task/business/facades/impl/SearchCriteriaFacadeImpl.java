package com.zephyr.task.business.facades.impl;

import com.zephyr.commons.anotations.Facade;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.protocol.dto.SearchCriteriaDto;
import com.zephyr.task.business.services.SearchCriteriaService;
import com.zephyr.task.core.domain.SearchCriteria;
import com.zephyr.task.facades.SearchCriteriaFacade;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Facade
public class SearchCriteriaFacadeImpl implements SearchCriteriaFacade {

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaService searchCriteriaService;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Flux<SearchCriteriaDto> findAll(Pageable pageable) {
        return searchCriteriaService.findAll(pageable)
                .map(mapper.mapperFor(SearchCriteriaDto.class));
    }

    @Override
    public Flux<SearchCriteriaDto> findByExample(Mono<SearchCriteriaDto> example, Sort sort) {
        return example.map(mapper.mapperFor(SearchCriteria.class))
                .flatMapMany(e -> searchCriteriaService.findAllByExample(e, sort))
                .map(mapper.mapperFor(SearchCriteriaDto.class));
    }
}
