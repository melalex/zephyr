package com.zephyr.task.facades;

import com.zephyr.data.dto.TaskDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskFacade {

    Flux<TaskDto> findAllForCurrentUser(String userId);

    Mono<Void> createTaskForCurrentUser(Mono<TaskDto> task);
}
