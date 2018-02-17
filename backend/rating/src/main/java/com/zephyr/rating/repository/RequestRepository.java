package com.zephyr.rating.repository;

import com.zephyr.rating.domain.Request;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RequestRepository extends ReactiveMongoRepository<Request, String> {

}
