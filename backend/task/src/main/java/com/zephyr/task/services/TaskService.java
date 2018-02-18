package com.zephyr.task.services;

import com.zephyr.task.domain.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface TaskService {

    Mono<Void> create(Task task, Principal principal);

    Flux<Task> findAll(Principal principal);

    Mono<Task> findById(String id);

    Mono<Void> update(Task task, Principal principal);

    Mono<Void> remove(String id, Principal principal);
}
