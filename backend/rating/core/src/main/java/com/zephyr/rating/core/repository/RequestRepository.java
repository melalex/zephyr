package com.zephyr.rating.core.repository;

import com.zephyr.rating.core.domain.Request;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RequestRepository extends ReactiveMongoRepository<Request, String> {

}
