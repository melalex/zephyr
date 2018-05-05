package com.zephyr.task.repositories;

import com.zephyr.task.domain.Task;
import reactor.core.publisher.Mono;

public interface TaskOperations {

    Mono<Task> findByUserIdAndIdOrShared(String userId, String id);
}
