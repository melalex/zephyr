package com.zephyr.task.services;

import com.zephyr.task.services.dto.TaskDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

    Flux<TaskDto> findAllForCurrentUser();

    Mono<Void> createTaskForCurrentUser(Mono<TaskDto> task);
}
