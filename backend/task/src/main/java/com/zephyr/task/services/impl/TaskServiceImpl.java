package com.zephyr.task.services.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.commons.ReactorUtils;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.Task;
import com.zephyr.task.domain.factories.MeteredSearchCriteriaFactory;
import com.zephyr.task.repositories.MeteredSearchCriteriaRepository;
import com.zephyr.task.repositories.SearchCriteriaRepository;
import com.zephyr.task.repositories.TaskRepository;
import com.zephyr.task.services.TaskService;
import com.zephyr.task.services.clients.AuthServiceClient;
import com.zephyr.task.services.dto.TaskDto;
import com.zephyr.task.services.gateways.NewCriteriaGateway;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    private static final String NEW_TASK_MESSAGE = "Received new task: {}";
    private static final String UPDATE_USAGE_MESSAGE = "Update usage of searchCriteria: {}";
    private static final String NEW_CRITERIA_MESSAGE = "Save new searchCriteria: {}";

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaRepository meteredSearchCriteriaRepository;

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaRepository searchCriteriaRepository;

    @Setter(onMethod = @__(@Autowired))
    private TaskRepository taskRepository;

    @Setter(onMethod = @__(@Autowired))
    private AuthServiceClient authServiceClient;

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaFactory meteredSearchCriteriaFactory;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Setter(onMethod = @__(@Autowired))
    private NewCriteriaGateway newCriteriaGateway;

    @Override
    public Flux<TaskDto> findAllForCurrentUser() {
        return taskRepository.findAllByUserId(authServiceClient.current().getName())
                .map(mapper.mapperFor(TaskDto.class));
    }

    @Override
    public Mono<Void> createTaskForCurrentUser(Mono<TaskDto> task) {
        return task.map(mapper.mapperFor(Task.class))
                .doOnNext(LoggingUtils.info(log, NEW_TASK_MESSAGE))
                .transform(ReactorUtils.doOnNextAsync(t -> saveSearchCriteria(t.getSearchCriteria())))
                .map(taskRepository::save)
                .then();
    }

    private Mono<Void> saveSearchCriteria(List<SearchCriteria> searchCriteria) {
        return Flux.fromIterable(searchCriteria)
                .flatMap(s ->
                        searchCriteriaRepository.findAll(Example.of(s))
                                .flatMap(m -> meteredSearchCriteriaRepository.updateUsage(m)
                                        .doOnNext(LoggingUtils.info(log, UPDATE_USAGE_MESSAGE))
                                )
                                .switchIfEmpty(searchCriteriaRepository.save(s)
                                        .flatMap(m -> meteredSearchCriteriaRepository.save(meteredSearchCriteriaFactory.create(m)))
                                        .doOnNext(LoggingUtils.info(log, NEW_CRITERIA_MESSAGE))
                                        .transform(ReactorUtils
                                                .<MeteredSearchCriteria>doOnNextAsync(t -> newCriteriaGateway.send(t.getSearchCriteria())))
                                )
                )
                .then();
    }
}
