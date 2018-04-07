package com.zephyr.task.repositories;

import com.zephyr.task.domain.SearchCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface SearchCriteriaRepository
        extends ReactiveMongoRepository<SearchCriteria, String>, SearchCriteriaOperations {

    Flux<SearchCriteria> findAll(Pageable pageable);
}
