package com.zephyr.task.services.impl;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.task.domain.factories.MeteredSearchCriteriaFactory;
import com.zephyr.task.repositories.MeteredSearchCriteriaRepository;
import com.zephyr.task.services.MeteredSearchCriteriaService;
import com.zephyr.task.services.dto.MeteredSearchCriteriaDto;
import com.zephyr.task.services.dto.SearchCriteriaDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class MeteredSearchCriteriaServiceImpl implements MeteredSearchCriteriaService {

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaRepository meteredSearchCriteriaRepository;

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaFactory meteredSearchCriteriaFactory;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Flux<MeteredSearchCriteriaDto> findAll(Pageable pageable) {
        return meteredSearchCriteriaRepository.findAll(pageable)
                .map(mapper.mapperFor(MeteredSearchCriteriaDto.class));
    }

    @Override
    public Flux<MeteredSearchCriteriaDto> findByExample(SearchCriteriaDto example, Sort sort) {
        return meteredSearchCriteriaRepository
                .findAll(meteredSearchCriteriaFactory.createExample(example), sort)
                .map(mapper.mapperFor(MeteredSearchCriteriaDto.class));
    }
}
