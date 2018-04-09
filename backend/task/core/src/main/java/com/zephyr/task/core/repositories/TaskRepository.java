package com.zephyr.task.core.repositories;

import com.zephyr.task.core.domain.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {

    Flux<Task> findAllByUserId(String userId);
}
