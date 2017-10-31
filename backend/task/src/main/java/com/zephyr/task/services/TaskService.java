package com.zephyr.task.services;

import reactor.core.publisher.Mono;

public interface TaskService {

    Mono<Void> placeTask();
}