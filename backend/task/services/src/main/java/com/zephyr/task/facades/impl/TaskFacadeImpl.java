package com.zephyr.task.facades.impl;

import com.zephyr.commons.anotations.Facade;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.task.domain.Task;
import com.zephyr.task.facades.TaskFacade;
import com.zephyr.task.services.TaskService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Facade
public class TaskFacadeImpl implements TaskFacade {

    @Setter(onMethod = @__(@Autowired))
    private TaskService taskService;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Mono<Void> create(Mono<TaskDto> task, Principal principal) {
        return task.map(mapper.mapperFor(Task.class))
                .flatMap(t -> taskService.create(t, principal));
    }

    @Override
    public Flux<TaskDto> findAll(Principal principal) {
        return taskService.findAll(principal)
                .map(mapper.mapperFor(TaskDto.class));
    }

    @Override
    public Mono<TaskDto> findById(String id) {
        return taskService.findById(id)
                .map(mapper.mapperFor(TaskDto.class));
    }

    @Override
    public Mono<Void> update(Mono<TaskDto> task, Principal principal) {
        return task.map(mapper.mapperFor(Task.class))
                .flatMap(t -> taskService.update(t, principal));
    }

    @Override
    public Mono<Void> remove(String id, Principal principal) {
        return taskService.remove(id, principal);
    }
}
