package com.zephyr.task.services.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.commons.interfaces.Assembler;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.integration.gateways.NewCriteriaGateway;
import com.zephyr.task.repositories.SearchCriteriaRepository;
import com.zephyr.task.services.ConfigurationService;
import com.zephyr.task.services.SearchCriteriaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

@Slf4j
@Service
@AllArgsConstructor
public class SearchCriteriaServiceImpl implements SearchCriteriaService {

    private static final String UPDATE_USAGE_MESSAGE = "Update usage of searchCriteria: {}";
    private static final String NEW_CRITERIA_MESSAGE = "Save new searchCriteria: {}";

    private SearchCriteriaRepository searchCriteriaRepository;
    private NewCriteriaGateway newCriteriaGateway;
    private ConfigurationService configurationService;
    private Assembler<SearchCriteria, QueryDto> queryAssembler;
    private Clock clock;

    @Override
    public Flux<SearchCriteria> findAll(Pageable pageable) {
        return searchCriteriaRepository.findAll(pageable);
    }

    @Override
    public Flux<SearchCriteria> findAllByExample(SearchCriteria example, Sort sort) {
        return searchCriteriaRepository.findAll(Example.of(example), sort);
    }

    @Override
    public Flux<SearchCriteria> findAllForUpdate() {
        TemporalAmount relevancePeriod = configurationService.getRelevancePeriod();
        Pageable pageable = configurationService.searchCriteriaOrder();

        return searchCriteriaRepository.findAllForUpdate(relevancePeriod, pageable);
    }

    @Override
    public Flux<SearchCriteria> updateSearchCriteria(SearchCriteria searchCriteria) {
        return searchCriteriaRepository.findAll(Example.of(searchCriteria))
                .flatMap(searchCriteriaRepository::updateUsage)
                .doOnNext(LoggingUtils.info(log, UPDATE_USAGE_MESSAGE))
                .switchIfEmpty(createSearchCriteriaFlow(searchCriteria));
    }

    private Mono<SearchCriteria> createSearchCriteriaFlow(SearchCriteria searchCriteria) {
        LocalDateTime now = LocalDateTime.now(clock);

        searchCriteria.setHitsCount(1);
        searchCriteria.setLastUpdate(now);
        searchCriteria.setLastHit(now);

        return queryAssembler.assemble(searchCriteria)
                .flatMap(newCriteriaGateway::send)
                .then(searchCriteriaRepository.save(searchCriteria))
                .doOnNext(LoggingUtils.info(log, NEW_CRITERIA_MESSAGE));
    }
}
