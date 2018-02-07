package com.zephyr.task.services.impl;

import com.zephyr.commons.ReactorUtils;
import com.zephyr.errors.domain.Actual;
import com.zephyr.errors.domain.Path;
import com.zephyr.errors.domain.Reasons;
import com.zephyr.errors.dsl.Problems;
import com.zephyr.task.domain.Task;
import com.zephyr.task.exceptions.OwnershipException;
import com.zephyr.task.repositories.TaskRepository;
import com.zephyr.task.services.SearchCriteriaService;
import com.zephyr.task.services.TaskService;
import com.zephyr.task.services.UserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    private static final String OWNERSHIP_ERROR_MESSAGE = "Requested task belongs to another User";
    private static final String NEW_TASK_MESSAGE = "Received new task: {}";
    private static final String USER_ID_FIELD = "userId";

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaService searchCriteriaService;

    @Setter(onMethod = @__(@Autowired))
    private TaskRepository taskRepository;

    @Setter(onMethod = @__(@Autowired))
    private UserService userService;

    @Override
    public Flux<Task> findAllForCurrentUser() {
        return userService.getCurrentUserId()
                .flatMapMany(taskRepository::findAllByUserId);
    }

    @Override
    public Mono<Task> findById(String id) {
        return taskRepository.findById(id)
                .transform(ReactorUtils.doOnNextAsync(this::checkOwner));
    }

    @Override
    public Mono<Void> createTaskForCurrentUser(Task task) {
        log.info(NEW_TASK_MESSAGE, task);
        return userService.getCurrentUserId()
                .doOnNext(task::setId)
                .then(createTask(task));
    }

    private Mono<Void> createTask(Task task) {
        return Flux.fromIterable(task.getSearchCriteria())
                .flatMap(searchCriteriaService::updateSearchCriteria)
                .then(taskRepository.save(task))
                .then();
    }

    private Mono<Void> checkOwner(Task task) {
        String ownerId = task.getId();
        return userService.getCurrentUserId()
                .filter(p -> !(task.isShared() || p.equals(ownerId)))
                .doOnNext(throwOwnershipException(ownerId))
                .then();
    }

    private Consumer<? super String> throwOwnershipException(String ownerId) {
        return id -> Problems.simpleException(new OwnershipException(OWNERSHIP_ERROR_MESSAGE))
                .status(HttpStatus.FORBIDDEN)
                .path(Path.of(Task.class).to(USER_ID_FIELD))
                .actual(Actual.isA(id))
                .reason(Reasons.NOT_MATCH)
                .payload(ownerId, id)
                .populateAndThrow();
    }
}
