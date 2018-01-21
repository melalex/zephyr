package com.zephyr.task.facades;

import com.zephyr.task.facades.dto.TaskDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskFacade {

    Flux<TaskDto> findAllForCurrentUser();

    Mono<Void> createTaskForCurrentUser(Mono<TaskDto> task);
}
