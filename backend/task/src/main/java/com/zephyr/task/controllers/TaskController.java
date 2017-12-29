package com.zephyr.task.controllers;

import com.zephyr.task.services.TaskService;
import com.zephyr.task.services.dto.TaskDto;
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
    private TaskService taskService;

    @GetMapping
    public Flux<TaskDto> findAllForCurrentUser() {
        return taskService.findAllForCurrentUser();
    }

    @PostMapping
    public Mono<Void> createTaskForCurrentUser(@Valid Mono<TaskDto> task) {
        return taskService.createTaskForCurrentUser(task);
    }
}
