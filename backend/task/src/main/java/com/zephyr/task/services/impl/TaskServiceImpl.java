package com.zephyr.task.services.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.commons.ReactorUtils;
import com.zephyr.errors.utils.ExceptionUtils;
import com.zephyr.task.domain.Task;
import com.zephyr.task.repositories.TaskRepository;
import com.zephyr.task.services.SearchCriteriaService;
import com.zephyr.task.services.TaskService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    private static final String CREATE_TASK_MESSAGE = "Received new task: {}";
    private static final String UPDATE_TASK_MESSAGE = "Updating task: {}";
    private static final String REMOVE_TASK_MESSAGE = "Removing task: {}";
    private static final String USER_ID_FIELD = "userId";

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaService searchCriteriaService;

    @Setter(onMethod = @__(@Autowired))
    private TaskRepository taskRepository;

    @Override
    public Mono<Void> create(Task task, Principal principal) {
        task.setId(null);
        task.setUserId(principal.getName());

        return Flux.fromIterable(task.getSearchCriteria())
                .flatMap(searchCriteriaService::updateSearchCriteria)
                .then(taskRepository.save(task))
                .doOnNext(LoggingUtils.info(log, Task::getId, CREATE_TASK_MESSAGE))
                .then();
    }

    @Override
    public Flux<Task> findAll(Principal principal) {
        return taskRepository.findAllByUserId(principal.getName());
    }

    @Override
    public Mono<Task> findById(String id) {
        return findById(id, null, false);
    }

    @Override
    public Mono<Void> update(Task task, Principal principal) {
        return findById(task.getId(), principal.getName(), true)
                .then(taskRepository.save(task))
                .doOnNext(LoggingUtils.info(log, Task::getId, UPDATE_TASK_MESSAGE))
                .then();
    }

    @Override
    public Mono<Void> remove(String id, Principal principal) {
        return findById(id, principal.getName(), true)
                .flatMap(taskRepository::delete)
                .doOnNext(v -> log.info(REMOVE_TASK_MESSAGE, id));
    }

    private Mono<Task> findById(String id, String principal, boolean checkOwner) {
        return taskRepository.findById(id)
                .doOnNext(ReactorUtils.conditional(checkOwner, t -> checkOwner(t, principal)))
                .switchIfEmpty(ExceptionUtils.notFound(Task.class, id));
    }

    private void checkOwner(Task task, String id) {
        if (!id.equalsIgnoreCase(task.getUserId())) {
            ExceptionUtils.notOwner(Task.class, USER_ID_FIELD, id);
        }
    }
}
