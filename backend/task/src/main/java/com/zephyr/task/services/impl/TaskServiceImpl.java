package com.zephyr.task.services.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.commons.ReactorUtils;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.task.domain.Task;
import com.zephyr.task.repositories.TaskRepository;
import com.zephyr.task.services.TaskService;
import com.zephyr.task.services.clients.AuthServiceClient;
import com.zephyr.task.services.dto.TaskDto;
import com.zephyr.task.services.internal.UpdatableSearchCriteriaService;
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
    private UpdatableSearchCriteriaService updatableSearchCriteriaService;

    @Setter(onMethod = @__(@Autowired))
    private TaskRepository taskRepository;

    @Setter(onMethod = @__(@Autowired))
    private AuthServiceClient authServiceClient;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Flux<TaskDto> findAllForCurrentUser() {
        return taskRepository.findAllByUserId(authServiceClient.current().getName())
                .map(mapper.mapperFor(TaskDto.class));
    }

    @Override
    public Mono<Void> createTaskForCurrentUser(Mono<TaskDto> task) {
        return task.map(mapper.mapperFor(Task.class))
                .doOnNext(LoggingUtils.info(log, NEW_TASK_MESSAGE))
                .transform(ReactorUtils.doOnNextAsync(t -> Flux.fromIterable(t.getSearchCriteria())
                        .flatMap(updatableSearchCriteriaService::updateSearchCriteria)))
                .map(taskRepository::save)
                .then();
    }
}
