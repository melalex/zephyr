package com.zephyr.task.facades.impl;

import com.zephyr.commons.anotations.Facade;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.dto.TaskDto;
import com.zephyr.task.domain.Task;
import com.zephyr.task.facades.TaskFacade;
import com.zephyr.task.services.TaskService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Facade
public class TaskFacadeImpl implements TaskFacade {

    @Setter(onMethod = @__(@Autowired))
    private TaskService taskService;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Flux<TaskDto> findAllForCurrentUser() {
        return taskService.findAllForCurrentUser()
                .map(mapper.mapperFor(TaskDto.class));
    }

    @Override
    public Mono<TaskDto> findById(String id) {
        return taskService.findById(id)
                .map(mapper.mapperFor(TaskDto.class));
    }

    @Override
    public Mono<Void> createTaskForCurrentUser(Mono<TaskDto> task) {
        return task.map(mapper.mapperFor(Task.class))
                .flatMap(t -> taskService.createTaskForCurrentUser(t));
    }
}
