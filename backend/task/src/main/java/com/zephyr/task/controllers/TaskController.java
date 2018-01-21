package com.zephyr.task.controllers;

import com.zephyr.task.facades.TaskFacade;
import com.zephyr.task.facades.dto.TaskDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @Setter(onMethod = @__(@Autowired))
    private TaskFacade taskFacade;

    @GetMapping
    public Flux<TaskDto> findAllForCurrentUser() {
        return taskFacade.findAllForCurrentUser();
    }

    @PostMapping
    public Mono<Void> createTaskForCurrentUser(@Valid Mono<TaskDto> task) {
        return taskFacade.createTaskForCurrentUser(task);
    }
}
