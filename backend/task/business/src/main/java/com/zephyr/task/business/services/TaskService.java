package com.zephyr.task.business.services;

import com.zephyr.task.domain.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface TaskService {

    Mono<Task> create(Task task, Principal principal);

    Flux<Task> findAll(Principal principal);

    Mono<Task> findById(String id);

    Mono<Task> update(Task task, Principal principal);

    Mono<Void> remove(String id, Principal principal);
}