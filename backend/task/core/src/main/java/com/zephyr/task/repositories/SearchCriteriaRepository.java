package com.zephyr.task.repositories;

import com.zephyr.task.domain.SearchCriteria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SearchCriteriaRepository extends ReactiveMongoRepository<SearchCriteria, String> {

}
