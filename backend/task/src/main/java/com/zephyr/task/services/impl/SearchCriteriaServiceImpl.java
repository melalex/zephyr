package com.zephyr.task.services.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.commons.ReactorUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                .switchIfEmpty(saveNewSearchCriteria(searchCriteria));
    }

    private Mono<MeteredSearchCriteria> saveNewSearchCriteria(SearchCriteria searchCriteria) {
        return searchCriteriaRepository.save(searchCriteria)
                .flatMap(m -> meteredSearchCriteriaRepository.save(meteredSearchCriteriaFactory.create(m)))
                .doOnNext(LoggingUtils.info(log, NEW_CRITERIA_MESSAGE))
                .transform(ReactorUtils.doOnNextAsync(t -> newCriteriaGateway.send(t.getSearchCriteria())));
    }
}
