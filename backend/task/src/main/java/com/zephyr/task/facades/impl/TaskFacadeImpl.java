package com.zephyr.task.facades.impl;

import com.zephyr.commons.anotations.Facade;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.task.services.TaskService;
import com.zephyr.task.domain.Task;
import com.zephyr.task.facades.TaskFacade;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Facade
@AllArgsConstructor
public class TaskFacadeImpl implements TaskFacade {

    private TaskService taskService;
    private ExtendedMapper mapper;

    @Override
    public Mono<TaskDto> create(Mono<TaskDto> task, Principal principal) {
        return task.map(mapper.mapperFor(Task.class))
                .flatMap(t -> taskService.create(t, principal))
                .map(mapper.mapperFor(TaskDto.class));
    }

    @Override
    public Flux<TaskDto> findAll(Principal principal) {
        return taskService.findAll(principal)
                .map(mapper.mapperFor(TaskDto.class));
    }

    @Override
    public Mono<TaskDto> findByUserAndId(String name, String id) {
        return taskService.findByUserAndId(name, id)
                .map(mapper.mapperFor(TaskDto.class));
    }

    @Override
    public Mono<Void> remove(String id, Principal principal) {
        return taskService.remove(id, principal);
    }
}
