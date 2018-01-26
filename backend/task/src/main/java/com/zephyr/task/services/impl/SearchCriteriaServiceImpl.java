package com.zephyr.task.services.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.commons.ReactorUtils;
import com.zephyr.commons.interfaces.Assembler;
import com.zephyr.data.dto.QueryDto;
import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.factories.MeteredSearchCriteriaFactory;
import com.zephyr.task.properties.TaskServiceProperties;
import com.zephyr.task.repositories.MeteredSearchCriteriaRepository;
import com.zephyr.task.repositories.SearchCriteriaRepository;
import com.zephyr.task.services.SearchCriteriaService;
import com.zephyr.task.integration.gateways.NewCriteriaGateway;
import com.zephyr.task.services.order.OrderProvider;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@Service
public class SearchCriteriaServiceImpl implements SearchCriteriaService {
    private static final String UPDATE_USAGE_MESSAGE = "Update usage of searchCriteria: {}";
    private static final String NEW_CRITERIA_MESSAGE = "Save new searchCriteria: {}";

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaRepository meteredSearchCriteriaRepository;

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaRepository searchCriteriaRepository;

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaFactory meteredSearchCriteriaFactory;

    @Setter(onMethod = @__(@Autowired))
    private NewCriteriaGateway newCriteriaGateway;

    @Setter(onMethod = @__(@Autowired))
    private TaskServiceProperties properties;

    @Setter(onMethod = @__(@Autowired))
    private OrderProvider orderProvider;

    @Setter(onMethod = @__(@Autowired))
    private Assembler<SearchCriteria, QueryDto> queryAssembler;

    @Override
    public Flux<MeteredSearchCriteria> findAll(Pageable pageable) {
        return meteredSearchCriteriaRepository.findAll(pageable);
    }

    @Override
    public Flux<MeteredSearchCriteria> findAllByExample(SearchCriteria example, Sort sort) {
        return meteredSearchCriteriaRepository
                .findAll(meteredSearchCriteriaFactory.createExample(example), sort);
    }

    @Override
    public Flux<SearchCriteria> findAllForUpdate() {
        return meteredSearchCriteriaRepository
                .findAllForUpdate(properties.getRelevancePeriod(), orderProvider.provide(properties.getBatchSize()))
                .map(MeteredSearchCriteria::getSearchCriteria);
    }

    @Override
    public Flux<MeteredSearchCriteria> updateSearchCriteria(SearchCriteria searchCriteria) {
        return searchCriteriaRepository.findAll(Example.of(searchCriteria))
                .flatMap(m -> meteredSearchCriteriaRepository.updateUsage(m))
                .doOnNext(LoggingUtils.info(log, UPDATE_USAGE_MESSAGE))
                .switchIfEmpty(createSearchCriteriaFlow(searchCriteria));
    }

    private Mono<MeteredSearchCriteria> createSearchCriteriaFlow(SearchCriteria searchCriteria) {
        MeteredSearchCriteria meteredSearchCriteria = meteredSearchCriteriaFactory.create(searchCriteria);

        return queryAssembler.assemble(searchCriteria)
                .transform(ReactorUtils.doOnNextAsync(newSearchCriteria(meteredSearchCriteria)))
                .transform(ReactorUtils.doOnNextAsync(q -> newCriteriaGateway.send(q)))
                .then(Mono.just(meteredSearchCriteria));
    }

    private Function<QueryDto, Publisher<?>> newSearchCriteria(MeteredSearchCriteria meteredSearchCriteria) {
        return q -> meteredSearchCriteriaRepository.save(meteredSearchCriteria)
                .doOnNext(LoggingUtils.info(log, NEW_CRITERIA_MESSAGE));
    }
}
