package com.zephyr.task.controllers;

import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.task.facades.TaskFacade;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/tasks")
public class TaskController {

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
    public Mono<TaskDto> findById(@PathVariable("id") String id) {
        return taskFacade.findById(id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> remove(@PathVariable("id") String id, Principal principal) {
        return taskFacade.remove(id, principal);
    }
}
