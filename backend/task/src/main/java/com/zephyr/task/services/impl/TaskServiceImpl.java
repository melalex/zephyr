package com.zephyr.task.services.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.errors.utils.ExceptionUtils;
import com.zephyr.task.domain.Task;
import com.zephyr.task.repositories.TaskRepository;
import com.zephyr.task.services.SearchCriteriaService;
import com.zephyr.task.services.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private static final String CREATE_TASK_MESSAGE = "Received new task with id [{}]";
    private static final String REMOVE_TASK_MESSAGE = "Removing task with id [{}] for user [{}]";

    private SearchCriteriaService searchCriteriaService;
    private TaskRepository taskRepository;

    @Override
    public Mono<Task> create(Task task, Principal principal) {
        task.setId(null);
        task.setUserId(principal.getName());

        return Flux.fromIterable(task.getSearchCriteria())
                .flatMap(searchCriteriaService::updateSearchCriteria)
                .then(taskRepository.save(task))
                .doOnNext(LoggingUtils.info(log, Task::getId, CREATE_TASK_MESSAGE));
    }

    @Override
    public Flux<Task> findAll(Principal principal) {
        return taskRepository.findAllByUserId(principal.getName());
    }

    @Override
    public Mono<Task> findByUserAndId(String name, String id) {
        return taskRepository.findByUserIdAndIdOrShared(name, id)
                .switchIfEmpty(ExceptionUtils.notFound(Task.class, id));
    }

    @Override
    public Mono<Void> remove(String id, Principal principal) {
        var userId = principal.getName();

        return taskRepository.findByUserIdAndId(userId, id)
                .switchIfEmpty(ExceptionUtils.notFound(Task.class, id))
                .flatMap(taskRepository::delete)
                .doOnNext(v -> log.info(REMOVE_TASK_MESSAGE, id, userId));
    }
}
