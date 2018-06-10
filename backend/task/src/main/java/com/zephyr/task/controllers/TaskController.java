package com.zephyr.task.controllers;

import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.task.facades.TaskFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TaskDto> create(@RequestBody @Valid Mono<TaskDto> task, Principal principal) {
        return taskFacade.create(task, principal);
    }

    @GetMapping
    public Flux<TaskDto> findAll(Principal principal) {
        return taskFacade.findAll(principal);
    }

    @GetMapping("/{name}/{id}")
    public Mono<TaskDto> findByUserAndId(@PathVariable String name, @PathVariable String id) {
        return taskFacade.findByUserAndId(name, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> remove(@PathVariable String id, Principal principal) {
        return taskFacade.remove(id, principal);
    }
}
