package com.zephyr.task.services.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.commons.ReactorUtils;
import com.zephyr.errors.domain.Actual;
import com.zephyr.errors.domain.Path;
import com.zephyr.errors.domain.Reasons;
import com.zephyr.errors.dsl.Problems;
import com.zephyr.errors.utils.ExceptionUtils;
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
    private static final String CREATE_TASK_MESSAGE = "Received new task: {}";
    private static final String UPDATE_TASK_MESSAGE = "Updating task: {}";
    private static final String REMOVE_TASK_MESSAGE = "Removing task: {}";
    private static final String USER_ID_FIELD = "userId";

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaService searchCriteriaService;

    @Setter(onMethod = @__(@Autowired))
    private TaskRepository taskRepository;

    @Setter(onMethod = @__(@Autowired))
    private UserService userService;

    @Override
    public Mono<Void> create(Task task) {
        task.setId(null);

        return userService.getCurrentUserId()
                .doOnNext(task::setUserId)
                .flatMapIterable(id -> task.getSearchCriteria())
                .flatMap(searchCriteriaService::updateSearchCriteria)
                .then(taskRepository.save(task))
                .doOnNext(LoggingUtils.info(log, Task::getId, CREATE_TASK_MESSAGE))
                .then();
    }

    @Override
    public Flux<Task> findAll() {
        return userService.getCurrentUserId()
                .flatMapMany(taskRepository::findAllByUserId);
    }

    @Override
    public Mono<Task> findById(String id) {
        return findById(id, true);
    }

    @Override
    public Mono<Void> update(Task task) {
        return findById(task.getId(), false)
                .then(taskRepository.save(task))
                .doOnNext(LoggingUtils.info(log, Task::getId, UPDATE_TASK_MESSAGE))
                .then();
    }

    @Override
    public Mono<Void> remove(String id) {
        return findById(id, false)
                .then(taskRepository.deleteById(id))
                .doOnNext(v -> log.info(REMOVE_TASK_MESSAGE, id));
    }

    private Mono<Task> findById(String id, boolean sharingEnabled) {
        return taskRepository.findById(id)
                .transform(ReactorUtils.doOnNextAsync(t -> checkOwner(t, sharingEnabled)))
                .switchIfEmpty(ExceptionUtils.notFound(Task.class, id));
    }

    private Mono<Void> checkOwner(Task task, boolean sharingEnabled) {
        String ownerId = task.getUserId();
        return userService.getCurrentUserId()
                .filter(p -> !p.equals(ownerId))
                .filter(p -> !(sharingEnabled && task.isShared()))
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
