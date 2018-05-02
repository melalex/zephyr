package com.zephyr.task.facades;

import com.zephyr.data.protocol.dto.TaskDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface TaskFacade {

    Mono<TaskDto> create(Mono<TaskDto> task, Principal principal);

    Flux<TaskDto> findAll(Principal principal);

    Mono<TaskDto> findById(String id);

    Mono<Void> remove(String id, Principal principal);
}
