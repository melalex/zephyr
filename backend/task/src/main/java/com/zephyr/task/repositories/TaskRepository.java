package com.zephyr.task.repositories;

import com.zephyr.task.domain.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {

}
