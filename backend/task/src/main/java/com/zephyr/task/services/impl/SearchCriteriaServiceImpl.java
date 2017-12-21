package com.zephyr.task.services.impl;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.factories.MeteredSearchCriteriaFactory;
import com.zephyr.task.integration.gateways.NewCriteriaGateway;
import com.zephyr.task.repositories.MeteredSearchCriteriaRepository;
import com.zephyr.task.repositories.SearchCriteriaRepository;
import com.zephyr.task.services.SearchCriteriaService;
import com.zephyr.task.services.dto.SearchCriteriaDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class SearchCriteriaServiceImpl implements SearchCriteriaService {

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
    public Flux<SearchCriteriaDto> findAll(Pageable pageable) {
        return meteredSearchCriteriaRepository.findAll(pageable)
                .map(mapper.mapperFor(SearchCriteriaDto.class));
    }

    private Mono<MeteredSearchCriteria> updateKeyword(SearchCriteria searchCriteria, Boolean isPresent) {
        if (isPresent) {
            log.info("Update usage of searchCriteria: {}", searchCriteria);
            return meteredSearchCriteriaRepository.updateUsage(searchCriteria);
        } else {
            log.info("Save new searchCriteria: {}", searchCriteria);
            return meteredSearchCriteriaRepository
                    .save(meteredSearchCriteriaFactory.create(searchCriteria))
                    .doOnSuccess(d -> newCriteriaGateway.send(d.getSearchCriteria()));
        }
    }
}
