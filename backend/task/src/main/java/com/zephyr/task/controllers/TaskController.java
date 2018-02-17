package com.zephyr.task.controllers;

import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.task.facades.TaskFacade;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @Setter(onMethod = @__(@Autowired))
    private TaskFacade taskFacade;

    @GetMapping
    public Flux<TaskDto> findAllForCurrent() {
        return taskFacade.findAllForCurrentUser();
    }

    @GetMapping("/{id}")
    public Mono<TaskDto> findById(@PathVariable("id") String id) {
        return taskFacade.findById(id);
    }

    @PostMapping
    public Mono<Void> createTaskForCurrentUser(@Valid Mono<TaskDto> task) {
        return taskFacade.createTaskForCurrentUser(task);
    }
}
