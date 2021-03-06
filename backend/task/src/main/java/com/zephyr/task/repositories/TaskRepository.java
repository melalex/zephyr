package com.zephyr.task.repositories;

import com.zephyr.task.domain.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskRepository extends ReactiveMongoRepository<Task, String>, TaskOperations {

    Flux<Task> findAllByUserId(String userId);

    Mono<Task> findByUserIdAndId(String userId, String id);
}
