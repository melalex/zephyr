package com.zephyr.task.services.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.commons.ReactorUtils;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.factories.MeteredSearchCriteriaFactory;
import com.zephyr.task.repositories.MeteredSearchCriteriaRepository;
import com.zephyr.task.repositories.SearchCriteriaRepository;
import com.zephyr.task.services.dto.MeteredSearchCriteriaDto;
import com.zephyr.task.services.dto.SearchCriteriaDto;
import com.zephyr.task.services.gateways.NewCriteriaGateway;
import com.zephyr.task.services.internal.UpdatableSearchCriteriaService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UpdatableSearchCriteriaServiceImpl implements UpdatableSearchCriteriaService {
    private static final String UPDATE_USAGE_MESSAGE = "Update usage of searchCriteria: {}";
    private static final String NEW_CRITERIA_MESSAGE = "Save new searchCriteria: {}";

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaRepository meteredSearchCriteriaRepository;

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaRepository searchCriteriaRepository;

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaFactory meteredSearchCriteriaFactory;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Setter(onMethod = @__(@Autowired))
    private NewCriteriaGateway newCriteriaGateway;

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

    @Override
    public Flux<MeteredSearchCriteria> updateSearchCriteria(SearchCriteria searchCriteria) {
        return searchCriteriaRepository.findAll(Example.of(searchCriteria))
                .flatMap(m -> meteredSearchCriteriaRepository.updateUsage(m)
                        .doOnNext(LoggingUtils.info(log, UPDATE_USAGE_MESSAGE)))
                .switchIfEmpty(saveNewSearchCriteria(searchCriteria));
    }

    private Mono<MeteredSearchCriteria> saveNewSearchCriteria(SearchCriteria searchCriteria) {
        return searchCriteriaRepository.save(searchCriteria)
                .flatMap(m -> meteredSearchCriteriaRepository.save(meteredSearchCriteriaFactory.create(m)))
                .doOnNext(LoggingUtils.info(log, NEW_CRITERIA_MESSAGE))
                .transform(ReactorUtils.doOnNextAsync(t -> newCriteriaGateway.send(t.getSearchCriteria())));
    }
}
