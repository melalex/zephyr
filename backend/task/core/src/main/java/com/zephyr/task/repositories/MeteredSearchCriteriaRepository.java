package com.zephyr.task.repositories;

import com.zephyr.task.domain.MeteredSearchCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MeteredSearchCriteriaRepository
        extends ReactiveMongoRepository<MeteredSearchCriteria, String>, MeteredSearchCriteriaOperations {

    Flux<MeteredSearchCriteria> findAll(Pageable pageable);
}