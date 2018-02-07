package com.zephyr.task.services;

import com.zephyr.task.domain.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

    Flux<Task> findAllForCurrentUser();

    Mono<Task> findById(String id);

    Mono<Void> createTaskForCurrentUser(Task task);
}
