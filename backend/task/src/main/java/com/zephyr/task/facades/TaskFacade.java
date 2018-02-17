package com.zephyr.task.facades;

import com.zephyr.data.protocol.dto.TaskDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskFacade {

    Mono<Void> create(Mono<TaskDto> task);

    Flux<TaskDto> findAll();

    Mono<TaskDto> findById(String id);

    Mono<Void> update(Mono<TaskDto> task);

    Mono<Void> remove(String id);
}
