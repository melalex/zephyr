package com.zephyr.task.services.impl;

import com.zephyr.task.domain.Task;
import com.zephyr.task.repositories.TaskRepository;
import com.zephyr.task.services.SearchCriteriaService;
import com.zephyr.task.services.TaskService;
import com.zephyr.task.services.UserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    private static final String NEW_TASK_MESSAGE = "Received new task: {}";

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaService searchCriteriaService;

    @Setter(onMethod = @__(@Autowired))
    private TaskRepository taskRepository;

    @Setter(onMethod = @__(@Autowired))
    private UserService userService;

    @Override
    public Flux<Task> findAllForCurrentUser() {
        return userService.getCurrentUserId()
                .flatMapMany(id -> taskRepository.findAllByUserId(id));
    }

    @Override
    public Mono<Void> createTaskForCurrentUser(Task task) {
        log.info(NEW_TASK_MESSAGE, task);
        return userService.getCurrentUserId()
                .doOnNext(task::setId)
                .then(saveSearchCriteria(task));
    }

    private Mono<Void> saveSearchCriteria(Task task) {
        return Flux.fromIterable(task.getSearchCriteria())
                .flatMap(searchCriteriaService::updateSearchCriteria)
                .then(taskRepository.save(task))
                .then();
    }
}
