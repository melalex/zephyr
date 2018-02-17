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

    @PostMapping
    public Mono<Void> create(@Valid Mono<TaskDto> task) {
        return taskFacade.create(task);
    }

    @GetMapping
    public Flux<TaskDto> findAll() {
        return taskFacade.findAll();
    }

    @GetMapping("/{id}")
    public Mono<TaskDto> findById(@PathVariable("id") String id) {
        return taskFacade.findById(id);
    }

    @PutMapping
    public Mono<Void> update(@Valid Mono<TaskDto> task) {
        return taskFacade.update(task);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> remove(@PathVariable("id") String id) {
        return taskFacade.remove(id);
    }
}
