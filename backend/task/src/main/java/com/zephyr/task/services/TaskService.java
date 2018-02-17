package com.zephyr.task.services;

import com.zephyr.task.domain.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

    Mono<Void> create(Task task);

    Flux<Task> findAll();

    Mono<Task> findById(String id);

    Mono<Void> update(Task task);

    Mono<Void> remove(String id);
}
