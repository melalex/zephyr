package com.zephyr.task.repositories;

import com.zephyr.task.domain.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {

    Flux<Task> findAllByUserId(String userId);
}
