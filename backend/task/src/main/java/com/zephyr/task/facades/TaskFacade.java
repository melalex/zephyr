package com.zephyr.task.facades;

import com.zephyr.data.protocol.dto.TaskDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskFacade {

    Flux<TaskDto> findAllForCurrentUser();

    Mono<TaskDto> findById(String id);

    Mono<Void> createTaskForCurrentUser(Mono<TaskDto> task);
}
