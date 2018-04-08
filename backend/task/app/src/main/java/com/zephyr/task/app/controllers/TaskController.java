package com.zephyr.task.app.controllers;

import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.task.facades.TaskFacade;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @Setter(onMethod = @__(@Autowired))
    private TaskFacade taskFacade;

    @PostMapping
    public Mono<TaskDto> create(@Valid Mono<TaskDto> task, Principal principal) {
        return taskFacade.create(task, principal);
    }

    @GetMapping
    public Flux<TaskDto> findAll(Principal principal) {
        return taskFacade.findAll(principal);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('server')")
    public Mono<TaskDto> findById(@PathVariable("id") String id) {
        return taskFacade.findById(id);
    }

    @PutMapping
    public Mono<TaskDto> update(@Valid Mono<TaskDto> task, Principal principal) {
        return taskFacade.update(task, principal);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> remove(@PathVariable("id") String id, Principal principal) {
        return taskFacade.remove(id, principal);
    }
}